package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataTestModule
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
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
class UserRepositoryImplTest : CustomKoinTest(dataTestModule) {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var database: AppDatabase

    @MockK
    private lateinit var userDao: UserDao

    private val usersRemoteDataSource: UserRemoteDataSource by inject()

    private val userResult = User(userId = USER_ID)
    private val userEntity = userResult.toUserEntity()

    private lateinit var userDataRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { database.userDao() } returns userDao

        userDataRepository = UserRepositoryImpl(database, usersRemoteDataSource)
    }

    @Test
    fun `test get users from network success`() {
        coEvery { userDao.getAll() } returns emptyList()
        coEvery { usersRemoteDataSource.users() } returns listOf(userResult)
        coEvery { userDao.insert(userEntity) } just Runs

        val result = runBlocking {
            userDataRepository.users()
        }

        coVerify { userDao.getAll() }
        coVerify { usersRemoteDataSource.users() }
        coVerify { userDao.insert(userEntity) }

        Assert.assertEquals(1, result.size)
        Assert.assertEquals(USER_ID, result[0].userId)
    }

    @Test
    fun `test get user from network success`() {
        coEvery { userDao.findById(USER_ID) } returns null
        coEvery { usersRemoteDataSource.user(USER_ID) } returns userResult
        coEvery { userDao.insert(userEntity) } just Runs

        val result = runBlocking {
            userDataRepository.user(USER_ID)
        }

        coVerify { userDao.findById(USER_ID) }
        coVerify { usersRemoteDataSource.user(USER_ID) }
        coVerify { userDao.insert(userEntity) }

        Assert.assertNotNull(result)
        Assert.assertEquals(USER_ID, result?.userId)
    }

    companion object {
        private const val USER_ID = 1
    }
}
