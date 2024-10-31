package com.jesusbadenas.kotlin_clean_compose_project.domain.interactors.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.domain.di.domainTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUsers
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
class GetUsersTest: CustomKoinTest(domainTestModule) {

    private val userRepository: UserRepository by inject()

    @Test
    fun testGetUsersUseCaseSuccess() {
        val user = User(USER_ID)
        coEvery { userRepository.users() } returns listOf(user)

        val getUsers = GetUsers(userRepository)
        val result = runBlocking { getUsers() }

        coVerify { userRepository.users() }
        Assert.assertEquals(user, result[0])
    }

    companion object {
        private const val USER_ID = 1
    }
}
