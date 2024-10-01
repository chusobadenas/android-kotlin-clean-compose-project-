package com.jesusbadenas.kotlin_clean_compose_project.domain.interactors.interactor

import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUser
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetUserTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

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
