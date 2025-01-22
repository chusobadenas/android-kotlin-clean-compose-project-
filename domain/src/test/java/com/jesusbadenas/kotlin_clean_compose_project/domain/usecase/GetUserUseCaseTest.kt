package com.jesusbadenas.kotlin_clean_compose_project.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.domain.di.domainTestModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toFlow
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinJUnit4Test
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = KoinTestApp::class)
class GetUserUseCaseTest : CustomKoinJUnit4Test(domainTestModule) {

    private val userRepository: UserRepository by inject()

    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getUserUseCase = GetUserUseCase(userRepository)
    }

    @Test
    fun `test get user success`() {
        val user = User(USER_ID)
        coEvery { userRepository.getUser(USER_ID) } returns user.toFlow()

        val result = runBlocking {
            getUserUseCase.execute(params = GetUserUseCase.Params(userId = USER_ID)).firstOrNull()
        }

        coVerify { userRepository.getUser(USER_ID) }
        Assert.assertNotNull(result)
        Assert.assertEquals(user, result)
    }

    companion object {
        private const val USER_ID = 1
    }
}
