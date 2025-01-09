package com.jesusbadenas.kotlin_clean_compose_project.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataTestModule
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class UserLocalDataSourceImplTest : CustomKoinTest(dataTestModule) {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val usersDao: UserDao by inject()

    private val exception = Exception()
    private val userEntity = UserEntity(id = USER_ID)

    private lateinit var dataSource: UserLocalDataSource

    @Before
    fun setUp() {
        dataSource = UserLocalDataSourceImpl(usersDao)
    }

    @Test
    fun `test get users error`() {
        coEvery { usersDao.getAll() } throws exception

        val result = runBlocking {
            dataSource.getUsers()
        }

        coVerify { usersDao.getAll() }

        Assert.assertNull(result)
    }

    @Test
    fun `test get users success`() {
        coEvery { usersDao.getAll() } returns listOf(userEntity)

        val result = runBlocking {
            dataSource.getUsers()
        }

        coVerify { usersDao.getAll() }

        Assert.assertEquals(1, result?.size)
        Assert.assertEquals(userEntity, result?.get(0))
    }

    @Test
    fun `test get user by id error`() {
        coEvery { usersDao.findById(USER_ID) } throws exception

        val result = runBlocking {
            dataSource.getUser(USER_ID)
        }

        coVerify { usersDao.findById(USER_ID) }

        Assert.assertNull(result)
    }

    @Test
    fun `test get user by id success`() {
        coEvery { usersDao.findById(USER_ID) } returns userEntity

        val result = runBlocking {
            dataSource.getUser(USER_ID)
        }

        coVerify { usersDao.findById(USER_ID) }

        Assert.assertNotNull(result)
        Assert.assertEquals(USER_ID, result?.id)
    }

    @Test
    fun `test insert users success`() {
        coEvery { usersDao.insert(userEntity) } just Runs

        runBlocking {
            dataSource.insertUsers(listOf(userEntity))
        }

        coVerify { usersDao.insert(userEntity) }
    }

    companion object {
        private const val USER_ID = 1
    }
}
