package com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toFlow
import com.jesusbadenas.kotlin_clean_compose_project.presentation.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIState
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinJUnit4Test
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.exception.TestException
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
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
class UserDetailsViewModelTest : CustomKoinJUnit4Test(presentationTestModule) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val getUserUseCase: GetUserUseCase by inject()

    private lateinit var viewModel: UserDetailsViewModel

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = UserDetailsViewModel(getUserUseCase)
    }

    @Test
    fun `test load user details error`() = coroutineRule.runTest {
        val exception = TestException()
        every {
            getUserUseCase.invoke(
                dispatcher = any(),
                params = GetUserUseCase.Params(userId = USER_ID)
            )
        } answers {
            flow {
                throw exception
            }
        }

        val emittedStates = mutableListOf<UIState<User>>()
        val job = launch {
            viewModel.uiState.toList(emittedStates)
        }

        viewModel.loadUser(USER_ID)

        advanceUntilIdle()
        job.cancel()

        Assert.assertEquals(2, emittedStates.size)
        Assert.assertTrue(emittedStates[0] is UIState.Loading)
        Assert.assertTrue(emittedStates[1] is UIState.Error)
        Assert.assertEquals(exception, (emittedStates[1] as? UIState.Error)?.exception)
    }

    @Test
    fun `test load user details success`() = coroutineRule.runTest {
        val user = User(USER_ID)
        every {
            getUserUseCase.invoke(
                dispatcher = any(),
                params = GetUserUseCase.Params(userId = USER_ID)
            )
        } answers {
            user.toFlow()
        }

        val emittedStates = mutableListOf<UIState<User>>()
        val job = launch {
            viewModel.uiState.toList(emittedStates)
        }

        viewModel.loadUser(USER_ID)

        advanceUntilIdle()
        job.cancel()

        Assert.assertEquals(2, emittedStates.size)
        Assert.assertTrue(emittedStates[0] is UIState.Loading)
        Assert.assertTrue(emittedStates[1] is UIState.Success)
        Assert.assertEquals(user, (emittedStates[1] as? UIState.Success)?.data)
    }

    companion object {
        private const val USER_ID = 1
    }
}
