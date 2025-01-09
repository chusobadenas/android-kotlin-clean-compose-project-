package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO
import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataTestModule
import com.jesusbadenas.kotlin_clean_compose_project.data.local.UserLocalDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
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
class UserRepositoryImplTest : CustomKoinTest(dataTestModule) {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val userLocalDataSource: UserLocalDataSource by inject()
    private val usersRemoteDataSource: UserRemoteDataSource by inject()

    private val userDTO = UserDTO(id = USER_ID)
    private val userResult = userDTO.toUser()
    private val userEntity = userResult.toUserEntity()

    private lateinit var userDataRepository: UserRepository

    @Before
    fun setUp() {
        userDataRepository = UserRepositoryImpl(userLocalDataSource, usersRemoteDataSource)
    }

    @Test
    fun `test get users from network success`() {
        coEvery { userLocalDataSource.getUsers() } returns emptyList()
        coEvery { usersRemoteDataSource.users() } returns listOf(userDTO)
        coEvery { userLocalDataSource.insertUsers(listOf(userEntity)) } just Runs

        val result = runBlocking {
            userDataRepository.users()
        }

        coVerify { userLocalDataSource.getUsers() }
        coVerify { usersRemoteDataSource.users() }
        coVerify { userLocalDataSource.insertUsers(listOf(userEntity)) }

        Assert.assertEquals(1, result?.size)
        Assert.assertEquals(userResult, result?.get(0))
    }

    @Test
    fun `test get user from network success`() {
        coEvery { userLocalDataSource.getUser(USER_ID) } returns null
        coEvery { usersRemoteDataSource.user(USER_ID) } returns userDTO
        coEvery { userLocalDataSource.insertUsers(listOf(userEntity)) } just Runs

        val result = runBlocking {
            userDataRepository.user(USER_ID)
        }

        coVerify { userLocalDataSource.getUser(USER_ID) }
        coVerify { usersRemoteDataSource.user(USER_ID) }
        coVerify { userLocalDataSource.insertUsers(listOf(userEntity)) }

        Assert.assertNotNull(result)
        Assert.assertEquals(userResult, result)
    }

    companion object {
        private const val USER_ID = 1
    }
}
