package com.jesusbadenas.kotlin_clean_compose_project.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toFlow
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toList
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinJUnit4Test
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.exception.TestException
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(application = KoinTestApp::class)
class UserLocalDataSourceImplTest : CustomKoinJUnit4Test(dataTestModule) {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val usersDao: UserDao by inject()

    private val exception = TestException()
    private val userEntity = UserEntity(id = USER_ID)

    private lateinit var dataSource: UserLocalDataSource

    @Before
    override fun setUp() {
        super.setUp()
        dataSource = UserLocalDataSourceImpl(usersDao)
    }

    @Test(expected = TestException::class)
    fun `test get users error`() {
        coEvery { usersDao.getAll() } throws exception

        runBlocking {
            dataSource.getUsers().firstOrNull()
        }

        coVerify { usersDao.getAll() }
    }

    @Test
    fun `test get users success`() {
        val users: Flow<List<UserEntity>> = userEntity.toList().toFlow()
        coEvery { usersDao.getAll() } returns users

        val result = runBlocking {
            dataSource.getUsers().firstOrNull()
        }

        coVerify { usersDao.getAll() }

        Assert.assertEquals(1, result?.size)
        Assert.assertEquals(userEntity, result?.get(0))
    }

    @Test(expected = TestException::class)
    fun `test get user by id error`() {
        coEvery { usersDao.getById(USER_ID) } throws exception

        runBlocking {
            dataSource.getUser(USER_ID).firstOrNull()
        }

        coVerify { usersDao.getById(USER_ID) }
    }

    @Test
    fun `test get user by id success`() {
        coEvery { usersDao.getById(USER_ID) } returns userEntity.toFlow()

        val result = runBlocking {
            dataSource.getUser(USER_ID).firstOrNull()
        }

        coVerify { usersDao.getById(USER_ID) }

        Assert.assertNotNull(result)
        Assert.assertEquals(USER_ID, result?.id)
    }

    @Test
    fun `test insert users success`() {
        val users = userEntity.toList()
        coEvery { usersDao.insert(users) } just Runs

        runBlocking {
            dataSource.insertUsers(listOf(userEntity))
        }

        coVerify { usersDao.insert(users) }
    }

    companion object {
        private const val USER_ID = 1
    }
}
