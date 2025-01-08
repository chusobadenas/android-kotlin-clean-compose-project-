package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataTestModule
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class UserRemoteDataSourceImplTest : CustomKoinTest(dataTestModule) {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val usersApi: UsersAPI by inject()

    private val exception = Exception()
    private val userResponse = UserResponse(userId = USER_ID)

    private lateinit var dataSource: UserRemoteDataSource

    @Before
    fun setUp() {
        dataSource = UserRemoteDataSourceImpl(usersApi)
    }

    @Test
    fun `test get users error`() {
        coEvery { usersApi.users() } throws exception

        val result = runBlocking {
            dataSource.users()
        }

        coVerify { usersApi.users() }

        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun `test get users success`() {
        coEvery { usersApi.users() } returns listOf(userResponse)

        val result = runBlocking {
            dataSource.users()
        }

        coVerify { usersApi.users() }

        Assert.assertEquals(1, result.size)
        Assert.assertEquals(USER_ID, result[0].userId)
    }

    @Test
    fun `test get user by id error`() {
        coEvery { usersApi.user(USER_ID) } throws exception

        val result = runBlocking {
            dataSource.user(USER_ID)
        }

        coVerify { usersApi.user(USER_ID) }

        Assert.assertNull(result)
    }

    @Test
    fun `test get user by id success`() {
        coEvery { usersApi.user(USER_ID) } returns userResponse

        val result = runBlocking {
            dataSource.user(USER_ID)
        }

        coVerify { usersApi.user(USER_ID) }

        Assert.assertNotNull(result)
        Assert.assertEquals(USER_ID, result?.userId)
    }

    companion object {
        private const val USER_ID = 1
    }
}
