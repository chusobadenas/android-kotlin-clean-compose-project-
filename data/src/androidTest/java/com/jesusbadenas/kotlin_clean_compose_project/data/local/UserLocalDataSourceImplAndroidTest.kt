package com.jesusbadenas.kotlin_clean_compose_project.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toList
import com.jesusbadenas.kotlin_clean_compose_project.test.rule.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserLocalDataSourceImplAndroidTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val userEntity = UserEntity(USER_ID)

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var userLocalDataSource: UserLocalDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = database.userDao()
        userLocalDataSource = UserLocalDataSourceImpl(userDao)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetUsersFromDatabaseSuccess() {
        val users = userEntity.toList()
        val result = runBlocking {
            userLocalDataSource.insertUsers(users)
            userLocalDataSource.getUsers().firstOrNull()
        }

        Assert.assertEquals(1, result?.size)
        Assert.assertEquals(userEntity.id, result?.get(0)?.id)
    }

    @Test
    fun testGetUserFromDatabaseSuccess() {
        val users = userEntity.toList()
        val result = runBlocking {
            userLocalDataSource.insertUsers(users)
            userLocalDataSource.getUser(USER_ID)
        }

        Assert.assertNotNull(result)
        Assert.assertEquals(userEntity.id, result?.id)
    }

    companion object {
        private const val USER_ID = 1
    }
}
