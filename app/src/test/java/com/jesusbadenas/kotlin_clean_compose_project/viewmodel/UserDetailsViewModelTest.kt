package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.di.presentationTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUser
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.extension.getOrAwaitValue
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.inject
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(application = KoinTestApp::class, sdk = [Build.VERSION_CODES.UPSIDE_DOWN_CAKE])
class UserDetailsViewModelTest: CustomKoinTest(presentationTestModule) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val getUser: GetUser by inject()

    @Test
    fun testLoadUserDetailsError() = coroutineRule.runTest {
        val exception = Exception()
        coEvery { getUser.invoke(USER_ID) } throws exception

        val userDetailsVM = UserDetailsViewModel(USER_ID, getUser)
        val error = userDetailsVM.uiError.getOrAwaitValue()

        assertEquals(exception, error.throwable)
    }

    @Test
    fun testLoadUserDetailsSuccess() = coroutineRule.runTest {
        val user = User(USER_ID)
        coEvery { getUser.invoke(USER_ID) } returns user

        val userDetailsVM = UserDetailsViewModel(USER_ID, getUser)
        val result = userDetailsVM.user.getOrAwaitValue()

        coVerify { getUser.invoke(USER_ID) }
        assertNotNull(result)
        assertEquals(USER_ID, result.userId)
    }

    companion object {
        private const val USER_ID = 1
    }
}
