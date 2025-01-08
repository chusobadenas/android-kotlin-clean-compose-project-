package com.jesusbadenas.kotlin_clean_compose_project.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.domain.di.domainTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = KoinTestApp::class)
class GetUsersUseCaseTest : CustomKoinTest(domainTestModule) {

    private val userRepository: UserRepository by inject()

    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        getUsersUseCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun `test get users success`() {
        val user = User(USER_ID)
        coEvery { userRepository.users() } returns listOf(user)

        val result = runBlocking {
            getUsersUseCase.execute()
        }

        coVerify { userRepository.users() }
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(user, result[0])
    }

    companion object {
        private const val USER_ID = 1
    }
}
