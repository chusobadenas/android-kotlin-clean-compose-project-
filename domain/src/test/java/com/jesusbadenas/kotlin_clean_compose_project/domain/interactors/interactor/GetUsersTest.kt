package com.jesusbadenas.kotlin_clean_compose_project.domain.interactors.interactor

import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUsers
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

class GetUsersTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

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
