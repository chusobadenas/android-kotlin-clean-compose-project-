package com.jesusbadenas.kotlin_clean_compose_project.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.extension.getOrAwaitValue
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.every
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class UserListViewModelTest : CustomKoinTest(presentationTestModule) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val getUsersUseCase: GetUsersUseCase by inject()

    private lateinit var viewModel: UserListViewModel

    @Before
    fun setUp() {
        viewModel = UserListViewModel(getUsersUseCase)
    }

    @Test
    fun `test load user list success`() = coroutineRule.runTest {
        val user = User(USER_ID)
        val userListResult = slot<(List<User>) -> Unit>()
        every {
            getUsersUseCase.invoke(scope = any(), coroutineExceptionHandler = any(), onResult = capture(userListResult))
        } answers {
            userListResult.captured(listOf(user))
        }

        viewModel.loadUserList()
        val userList = viewModel.userList.getOrAwaitValue()

        Assert.assertEquals(1, userList.size)
        Assert.assertEquals(user, userList[0])
    }

    companion object {
        private const val USER_ID = 1
    }
}
