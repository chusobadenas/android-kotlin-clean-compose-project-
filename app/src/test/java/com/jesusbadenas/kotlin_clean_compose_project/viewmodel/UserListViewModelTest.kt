package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUsers
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.extension.getOrAwaitValue
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(application = KoinTestApp::class, sdk = [Build.VERSION_CODES.UPSIDE_DOWN_CAKE])
class UserListViewModelTest : CustomKoinTest(presentationTestModule) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val getUsers: GetUsers by inject()

    @Test
    fun testLoadUserListError() = coroutineRule.runTest {
        val exception = Exception()
        coEvery { getUsers.invoke() } throws exception

        val userListVM = UserListViewModel(getUsers)
        val error = userListVM.uiError.getOrAwaitValue()

        assertEquals(exception, error.throwable)
    }

    @Test
    fun testLoadUserListSuccess() = coroutineRule.runTest {
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
