package com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.di.presentationTestModule
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
class UserDetailsViewModelTest : CustomKoinTest(presentationTestModule) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val getUserUseCase: GetUserUseCase by inject()

    private lateinit var viewModel: UserDetailsViewModel

    @Before
    fun setUp() {
        viewModel = UserDetailsViewModel(getUserUseCase)
    }

    @Test
    fun `test load user details error`() = coroutineRule.runTest {
        val exception = Exception()
        every {
            getUserUseCase.invoke(
                scope = any(),
                params = GetUserUseCase.Params(userId = USER_ID),
                onError = any(),
                onResult = any()
            )
        } throws exception

        viewModel.loadUser(USER_ID)
        val uiError = viewModel.uiError.getOrAwaitValue()

        Assert.assertNotNull(uiError)
        Assert.assertEquals(R.string.btn_text_retry, uiError.buttonTextId)
        Assert.assertEquals(R.string.error_message_generic, uiError.messageTextId)
        Assert.assertEquals(exception, uiError.throwable)
    }

    @Test
    fun `test load user details null`() = coroutineRule.runTest {
        val userDetailsError = slot<(Throwable) -> Unit>()
        val userDetailsResult = slot<(User?) -> Unit>()
        every {
            getUserUseCase.invoke(
                scope = any(),
                params = GetUserUseCase.Params(userId = USER_ID),
                onError = capture(userDetailsError),
                onResult = capture(userDetailsResult)
            )
        } answers {
            userDetailsResult.captured(null)
        }

        viewModel.loadUser(USER_ID)
        val uiError = viewModel.uiError.getOrAwaitValue()

        Assert.assertNotNull(uiError)
        Assert.assertEquals(R.string.btn_text_retry, uiError.buttonTextId)
        Assert.assertEquals(R.string.error_message_generic, uiError.messageTextId)
        Assert.assertNull(uiError.throwable)
    }

    @Test
    fun `test load user details success`() = coroutineRule.runTest {
        val user = User(USER_ID)
        val userDetailsError = slot<(Throwable) -> Unit>()
        val userDetailsResult = slot<(User?) -> Unit>()
        every {
            getUserUseCase.invoke(
                scope = any(),
                params = GetUserUseCase.Params(userId = USER_ID),
                onError = capture(userDetailsError),
                onResult = capture(userDetailsResult)
            )
        } answers {
            userDetailsResult.captured(user)
        }

        viewModel.loadUser(USER_ID)
        val result = viewModel.user.getOrAwaitValue()

        Assert.assertNotNull(result)
        Assert.assertEquals(user, result)
    }

    companion object {
        private const val USER_ID = 1
    }
}
