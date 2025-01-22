package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataTestModule
import com.jesusbadenas.kotlin_clean_compose_project.data.local.UserLocalDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toFlow
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toList
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinJUnit4Test
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
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
class UserRepositoryImplTest : CustomKoinJUnit4Test(dataTestModule) {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val userLocalDataSource: UserLocalDataSource by inject()
    private val usersRemoteDataSource: UserRemoteDataSource by inject()

    private val userDTO = UserDTO(id = USER_ID)
    private val userResult = userDTO.toUser()
    private val userEntity = userResult.toUserEntity()

    private lateinit var userDataRepository: UserRepository

    @Before
    override fun setUp() {
        super.setUp()
        userDataRepository = UserRepositoryImpl(userLocalDataSource, usersRemoteDataSource)
    }

    @Test
    fun `test get users from network success`() {
        val emptyLocalUsers = emptyList<UserEntity>().toFlow()
        val localUsers = userEntity.toList()
        val remoteUsers = userDTO.toList().toFlow()
        coEvery { userLocalDataSource.getUsers() } returns emptyLocalUsers
        coEvery { usersRemoteDataSource.getUsers() } returns remoteUsers
        coEvery { userLocalDataSource.insertUsers(localUsers) } just Runs

        val result = runBlocking {
            userDataRepository.getUsers().firstOrNull()
        }

        coVerify { userLocalDataSource.getUsers() }
        coVerify { usersRemoteDataSource.getUsers() }
        coVerify { userLocalDataSource.insertUsers(localUsers) }

        Assert.assertEquals(1, result?.size)
        Assert.assertEquals(userResult, result?.get(0))
    }

    @Test
    fun `test get users from database success`() {
        val localUsers = userEntity.toList()
        coEvery { userLocalDataSource.getUsers() } returns localUsers.toFlow()

        val result = runBlocking {
            userDataRepository.getUsers().firstOrNull()
        }

        coVerify { userLocalDataSource.getUsers() }

        Assert.assertEquals(1, result?.size)
        Assert.assertEquals(USER_ID, result?.get(0)?.id)
    }

    @Test
    fun `test get user from network success`() {
        coEvery { userLocalDataSource.getUser(USER_ID) } returns flowOf(null)
        coEvery { usersRemoteDataSource.getUser(USER_ID) } returns userDTO.toFlow()
        coEvery { userLocalDataSource.insertUsers(listOf(userEntity)) } just Runs

        val result = runBlocking {
            userDataRepository.getUser(USER_ID).firstOrNull()
        }

        coVerify { userLocalDataSource.getUser(USER_ID) }
        coVerify { usersRemoteDataSource.getUser(USER_ID) }
        coVerify { userLocalDataSource.insertUsers(listOf(userEntity)) }

        Assert.assertNotNull(result)
        Assert.assertEquals(userResult, result)
    }

    @Test
    fun `test get user from database success`() {
        coEvery { userLocalDataSource.getUser(USER_ID) } returns userEntity.toFlow()

        val result = runBlocking {
            userDataRepository.getUser(USER_ID).firstOrNull()
        }

        coVerify { userLocalDataSource.getUser(USER_ID) }

        Assert.assertNotNull(result)
        Assert.assertEquals(USER_ID, result?.id)
    }

    companion object {
        private const val USER_ID = 1
    }
}
