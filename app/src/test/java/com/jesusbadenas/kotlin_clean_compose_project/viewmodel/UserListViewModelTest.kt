package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUsers
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
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var getUsers: GetUsers

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testLoadUserListError() = coroutineRule.runBlockingTest {
        val exception = Exception()
        coEvery { getUsers.invoke() } throws exception

        val userListVM = UserListViewModel(getUsers)
        val error = userListVM.uiError.getOrAwaitValue()

        assertEquals(exception, error.throwable)
    }

    @Test
    fun testLoadUserListSuccess() = coroutineRule.runBlockingTest {
        val user = User(USER_ID)
        coEvery { getUsers.invoke() } returns listOf(user)

        val userListVM = UserListViewModel(getUsers)
        val userList = userListVM.userList.getOrAwaitValue()

        coVerify { getUsers.invoke() }
        assertFalse(userList.isNullOrEmpty())
        assertEquals(userList[0].userId, USER_ID)
    }

    companion object {
        private const val USER_ID = 1
    }
}
