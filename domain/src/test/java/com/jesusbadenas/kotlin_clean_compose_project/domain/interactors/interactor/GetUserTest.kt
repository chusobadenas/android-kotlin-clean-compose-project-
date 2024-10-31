package com.jesusbadenas.kotlin_clean_compose_project.domain.interactors.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.domain.di.domainTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUser
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.inject
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = KoinTestApp::class)
class GetUserTest: CustomKoinTest(domainTestModule) {

    private val userRepository: UserRepository by inject()

    @Test
    fun testGetUserUseCaseSuccess() {
        val user = User(USER_ID)
        coEvery { userRepository.user(USER_ID) } returns user

        val getUser = GetUser(userRepository)
        val result = runBlocking { getUser(USER_ID) }

        coVerify { userRepository.user(USER_ID) }
        Assert.assertEquals(user, result)
    }

    companion object {
        private const val USER_ID = 1
    }
}
