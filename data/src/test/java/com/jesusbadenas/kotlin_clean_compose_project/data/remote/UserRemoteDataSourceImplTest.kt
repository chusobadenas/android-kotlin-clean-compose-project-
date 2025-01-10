package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO
import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toList
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinJUnit4Test
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
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
class UserRemoteDataSourceImplTest : CustomKoinJUnit4Test(dataTestModule) {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val usersApi: UsersAPI by inject()

    private val exception = Exception()
    private val userDTO = UserDTO(id = USER_ID)

    private lateinit var dataSource: UserRemoteDataSource

    @Before
    override fun setUp() {
        super.setUp()
        dataSource = UserRemoteDataSourceImpl(usersApi)
    }

    @Test
    fun `test get users error`() {
        coEvery { usersApi.users() } throws exception

        val result = runBlocking {
            dataSource.getUsers().firstOrNull()
        }

        coVerify { usersApi.users() }

        Assert.assertNull(result)
    }

    @Test
    fun `test get users success`() {
        val users = userDTO.toList()
        coEvery { usersApi.users() } returns users

        val result = runBlocking {
            dataSource.getUsers().firstOrNull()
        }

        coVerify { usersApi.users() }

        Assert.assertNotNull(result)
        Assert.assertEquals(1, result?.size)
        Assert.assertEquals(userDTO, result?.get(0))
    }

    @Test
    fun `test get user by id error`() {
        coEvery { usersApi.user(USER_ID) } throws exception

        val result = runBlocking {
            dataSource.getUser(USER_ID)
        }

        coVerify { usersApi.user(USER_ID) }

        Assert.assertNull(result)
    }

    @Test
    fun `test get user by id success`() {
        coEvery { usersApi.user(USER_ID) } returns userDTO

        val result = runBlocking {
            dataSource.getUser(USER_ID)
        }

        coVerify { usersApi.user(USER_ID) }

        Assert.assertNotNull(result)
        Assert.assertEquals(userDTO, result)
    }

    companion object {
        private const val USER_ID = 1
    }
}
