package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import com.jesusbadenas.kotlin_clean_compose_project.data.api.APIService
import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDataRepository(
    private val apiService: APIService,
    appDatabase: AppDatabase
) : UserRepository {

    private val userDao = appDatabase.userDao()

    override suspend fun users(): List<User> =
        withContext(Dispatchers.IO) {
            // Get from database first
            val dbUsers = userDao.getAll()
            if (dbUsers.isEmpty()) {
                // If not found, get from API
                apiService.userDataList().map { userResponse ->
                    userResponse.toUser()
                }.also { userList ->
                    val entities = userList.map { it.toUserEntity() }.toTypedArray()
                    userDao.insert(*entities)
                }
            } else {
                dbUsers.map { it.toUser() }
            }
        }

    override suspend fun user(userId: Int): User =
        withContext(Dispatchers.IO) {
            // Get from database first
            userDao.findById(userId)?.toUser()
            // If not found, get from API
                ?: apiService.userDataById(userId).toUser().also { user ->
                    userDao.insert(user.toUserEntity())
                }
        }
}
