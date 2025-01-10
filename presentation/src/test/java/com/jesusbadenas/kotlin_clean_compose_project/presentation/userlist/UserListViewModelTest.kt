package com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toFlow
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toList
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinJUnit4Test
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.extension.getOrAwaitValue
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.coEvery
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
class UserListViewModelTest : CustomKoinJUnit4Test(presentationTestModule) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val getUsersUseCase: GetUsersUseCase by inject()

    private lateinit var viewModel: UserListViewModel

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = UserListViewModel(getUsersUseCase)
    }

    @Test
    fun `test load user list error`() = coroutineRule.runTest {
        val exception = Exception()
        coEvery {
            getUsersUseCase.invoke(dispatcher = any())
        } throws exception

        viewModel.loadUserList()
        val uiError = viewModel.uiError.getOrAwaitValue()

        Assert.assertNotNull(uiError)
        Assert.assertEquals(R.string.btn_text_retry, uiError.buttonTextId)
        Assert.assertEquals(R.string.error_message_generic, uiError.messageTextId)
        Assert.assertEquals(exception, uiError.throwable)
    }

    @Test
    fun `test load user list empty`() = coroutineRule.runTest {
        coEvery {
            getUsersUseCase.invoke(dispatcher = any())
        } answers {
            emptyList<User>().toFlow()
        }

        viewModel.loadUserList()
        val uiError = viewModel.uiError.getOrAwaitValue()

        Assert.assertNotNull(uiError)
        Assert.assertEquals(R.string.btn_text_retry, uiError.buttonTextId)
        Assert.assertEquals(R.string.error_message_empty_list, uiError.messageTextId)
        Assert.assertNull(uiError.throwable)
    }

    @Test
    fun `test load user list success`() = coroutineRule.runTest {
        val user = User(USER_ID)
        coEvery {
            getUsersUseCase.invoke(dispatcher = any())
        } answers {
            user.toList().toFlow()
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
