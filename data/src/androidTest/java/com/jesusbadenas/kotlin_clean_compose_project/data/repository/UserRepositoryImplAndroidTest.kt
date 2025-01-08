package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserRepositoryImplAndroidTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @MockK
    private lateinit var usersRemoteDataSource: UserRemoteDataSource

    private val userResponse = UserResponse(USER_ID)

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var userDataRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = database.userDao()
        userDataRepository = UserRepositoryImpl(database, usersRemoteDataSource)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `test get users from database success`() {
        val array = listOf(userResponse).map { it.toUser().toUserEntity() }.toTypedArray()
        runBlocking { userDao.insert(*array) }

        val result = runBlocking {
            userDataRepository.users()
        }

        Assert.assertEquals(1, result.size)
        Assert.assertEquals(USER_ID, result[0].userId)
    }

    @Test
    fun `test get user from database success`() {
        val userEntity = userResponse.toUser().toUserEntity()
        runBlocking { userDao.insert(userEntity) }

        val result = runBlocking {
            userDataRepository.user(USER_ID)
        }

        Assert.assertNotNull(result)
        Assert.assertEquals(USER_ID, result?.userId)
    }

    companion object {
        private const val USER_ID = 1
    }
}
