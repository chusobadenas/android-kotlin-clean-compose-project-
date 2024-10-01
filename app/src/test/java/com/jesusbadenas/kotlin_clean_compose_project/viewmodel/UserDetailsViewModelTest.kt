package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUser
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.test.CoroutinesTestRule
import com.jesusbadenas.kotlin_clean_compose_project.test.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var getUser: GetUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testLoadUserDetailsError() = coroutineRule.runBlockingTest {
        val exception = Exception()
        coEvery { getUser.invoke(USER_ID) } throws exception

        val userDetailsVM = UserDetailsViewModel(USER_ID, getUser)
        val error = userDetailsVM.uiError.getOrAwaitValue()

        assertEquals(exception, error.throwable)
    }

    @Test
    fun testLoadUserDetailsSuccess() = coroutineRule.runBlockingTest {
        val user = User(USER_ID)
        coEvery { getUser.invoke(USER_ID) } returns user

        val userDetailsVM = UserDetailsViewModel(USER_ID, getUser)
        val result = userDetailsVM.user.getOrAwaitValue()

        coVerify { getUser.invoke(USER_ID) }
        assertNotNull(result)
        assertEquals(USER_ID, result.userId)
    }

    companion object {
        private const val USER_ID = 1
    }
}
