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
class GetUserUseCaseTest : CustomKoinTest(domainTestModule) {

    private val userRepository: UserRepository by inject()

    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setUp() {
        getUserUseCase = GetUserUseCase(userRepository)
    }

    @Test
    fun `test get user success`() {
        val user = User(USER_ID)
        coEvery { userRepository.getUser(USER_ID) } returns user

        val result = runBlocking {
            getUserUseCase.execute(params = GetUserUseCase.Params(userId = USER_ID))
        }

        coVerify { userRepository.getUser(USER_ID) }
        Assert.assertEquals(user, result)
    }

    companion object {
        private const val USER_ID = 1
    }
}
