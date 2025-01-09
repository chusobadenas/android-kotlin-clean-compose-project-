package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    appDatabase: AppDatabase,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    private val userDao = appDatabase.userDao()

    override suspend fun users(): List<User>? =
        withContext(Dispatchers.IO) {
            // Get from database first
            val dbUsers = userDao.getAll()
            if (dbUsers.isEmpty()) {
                // If not found, get from server
                userRemoteDataSource.users().also { userList ->
                    val entities = userList?.map {
                        it.toUserEntity()
                    }.orEmpty().toTypedArray()
                    userDao.insert(*entities)
                }
            } else {
                dbUsers.map { it.toUser() }
            }
        }

    override suspend fun user(userId: Int): User? =
        withContext(Dispatchers.IO) {
            // Get from database first
            userDao.findById(userId)?.toUser()
            // If not found, get from server
                ?: userRemoteDataSource.user(userId)?.also { user ->
                    userDao.insert(user.toUserEntity())
                }
        }
}
