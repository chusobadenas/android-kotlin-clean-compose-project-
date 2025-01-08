package com.jesusbadenas.kotlin_clean_compose_project.userdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
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
        val userDetailsResult = slot<(User?) -> Unit>()
        every {
            getUserUseCase.invoke(
                scope = any(),
                coroutineExceptionHandler = any(),
                params = GetUserUseCase.Params(userId = USER_ID),
                onResult = capture(userDetailsResult)
            )
        } answers {
            userDetailsResult.captured(null)
        }

        viewModel.loadUser(USER_ID)
        val error = viewModel.uiError.getOrAwaitValue()

        Assert.assertNotNull(error)
    }

    @Test
    fun `test load user details success`() = coroutineRule.runTest {
        val user = User(USER_ID)
        val userDetailsResult = slot<(User?) -> Unit>()
        every {
            getUserUseCase.invoke(
                scope = any(),
                coroutineExceptionHandler = any(),
                params = GetUserUseCase.Params(userId = USER_ID),
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
