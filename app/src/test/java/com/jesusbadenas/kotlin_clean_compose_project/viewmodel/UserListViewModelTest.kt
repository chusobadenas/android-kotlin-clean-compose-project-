package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.extension.getOrAwaitValue
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import com.jesusbadenas.kotlin_clean_compose_project.userlist.UserListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(application = KoinTestApp::class)
class UserListViewModelTest : CustomKoinTest(presentationTestModule) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val getUsersUseCase: GetUsersUseCase by inject()

    private val viewModel = UserListViewModel(getUsersUseCase)

    @Test
    fun `test load user list error`() = coroutineRule.runTest {
        val exception = Exception()
        coEvery { getUsersUseCase.execute() } throws exception

        viewModel.loadUserList()
        val error = viewModel.uiError.getOrAwaitValue()

        coVerify { getUsersUseCase.execute() }
        Assert.assertEquals(exception, error.throwable)
    }

    @Test
    fun `test load user list success`() = coroutineRule.runTest {
        val user = User(USER_ID)
        coEvery { getUsersUseCase.execute() } returns listOf(user)

        viewModel.loadUserList()
        val userList = viewModel.userList.getOrAwaitValue()

        coVerify { getUsersUseCase.execute() }
        Assert.assertEquals(1, userList.size)
        Assert.assertEquals(USER_ID, userList[0].userId)
    }

    companion object {
        private const val USER_ID = 1
    }
}
