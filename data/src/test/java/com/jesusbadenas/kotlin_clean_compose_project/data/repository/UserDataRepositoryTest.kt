package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import com.jesusbadenas.kotlin_clean_compose_project.data.api.APIService
import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.jesusbadenas.kotlin_clean_compose_project.test.CoroutinesTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDataRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var apiService: APIService

    @MockK
    private lateinit var database: AppDatabase

    @MockK
    private lateinit var userDao: UserDao

    private val userResponse = UserResponse(USER_ID)

    private lateinit var userDataRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { database.userDao() } returns userDao

        userDataRepository = UserDataRepository(apiService, database)
    }

    @Test
    fun testGetUsersFromNetworkSuccess() {
        coEvery { userDao.getAll() } returns emptyList()
        coEvery { apiService.userDataList() } returns listOf(userResponse)
        coEvery { userDao.insert(any()) } just Runs

        val result = runBlocking { userDataRepository.users() }

        coVerify { userDao.getAll() }
        coVerify { apiService.userDataList() }
        coVerify { userDao.insert(any()) }

        assertTrue(result.isNotEmpty())
        assertSame(result.size, 1)
        assertSame(result[0].userId, USER_ID)
    }

    @Test
    fun testGetUserByIdFromNetworkSuccess() {
        coEvery { userDao.findById(USER_ID) } returns null
        coEvery { apiService.userDataById(USER_ID) } returns userResponse
        coEvery { userDao.insert(any()) } just Runs

        val result = runBlocking { userDataRepository.user(USER_ID) }

        coVerify { userDao.findById(USER_ID) }
        coVerify { apiService.userDataById(USER_ID) }
        coVerify { userDao.insert(any()) }

        assertSame(result.userId, USER_ID)
    }

    companion object {
        private const val USER_ID = 1
    }
}
