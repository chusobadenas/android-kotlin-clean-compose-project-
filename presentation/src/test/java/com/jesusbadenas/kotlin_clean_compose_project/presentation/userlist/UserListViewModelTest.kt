package com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toFlow
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toList
import com.jesusbadenas.kotlin_clean_compose_project.presentation.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIState
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinJUnit4Test
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.exception.TestException
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
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
        val exception = TestException()
        coEvery {
            getUsersUseCase.invoke(dispatcher = any())
        } answers {
            flow {
                throw exception
            }
        }

        viewModel.loadUserList()
        val uiState = viewModel.uiState.last()

        Assert.assertTrue(uiState is UIState.Error)
        Assert.assertEquals(exception, (uiState as? UIState.Error)?.exception)
    }

    @Test
    fun `test load user list empty`() = coroutineRule.runTest {
        coEvery {
            getUsersUseCase.invoke(dispatcher = any())
        } answers {
            emptyList<User>().toFlow()
        }

        viewModel.loadUserList()
        val uiState = viewModel.uiState.last()

        Assert.assertTrue(uiState is UIState.Empty)
    }

    @Test
    fun `test load user list success`() = coroutineRule.runTest {
        val users = User(USER_ID).toList()
        coEvery {
            getUsersUseCase.invoke(dispatcher = any())
        } answers {
            users.toFlow()
        }

        viewModel.loadUserList()
        val uiState = viewModel.uiState.last()

        Assert.assertTrue(uiState is UIState.Success)
        Assert.assertEquals(users, (uiState as? UIState.Success)?.data)
    }

    companion object {
        private const val USER_ID = 1
    }
}
