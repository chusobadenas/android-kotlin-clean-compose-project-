package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.extension.getOrAwaitValue
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.inject
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(application = KoinTestApp::class, sdk = [Build.VERSION_CODES.UPSIDE_DOWN_CAKE])
class UserDetailsViewModelTest : CustomKoinTest(presentationTestModule) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val getUserUseCase: GetUserUseCase by inject()

    private val viewModel = UserDetailsViewModel(userId = USER_ID, getUserUseCase = getUserUseCase)

    @Test
    fun `test load user details error`() = coroutineRule.runTest {
        val exception = Exception()
        coEvery {
            getUserUseCase.execute(params = GetUserUseCase.Params(userId = USER_ID))
        } throws exception

        val error = viewModel.uiError.getOrAwaitValue()

        coVerify {
            getUserUseCase.execute(params = GetUserUseCase.Params(userId = USER_ID))
        }
        Assert.assertEquals(exception, error.throwable)
    }

    @Test
    fun `test load user details success`() = coroutineRule.runTest {
        val user = User(USER_ID)
        coEvery {
            getUserUseCase.execute(params = GetUserUseCase.Params(userId = USER_ID))
        } returns user

        val result = viewModel.user.getOrAwaitValue()

        coVerify {
            getUserUseCase.execute(params = GetUserUseCase.Params(userId = USER_ID))
        }
        Assert.assertNotNull(result)
        Assert.assertEquals(USER_ID, result.userId)
    }

    companion object {
        private const val USER_ID = 1
    }
}
