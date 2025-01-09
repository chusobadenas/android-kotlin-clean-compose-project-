package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import com.jesusbadenas.kotlin_clean_compose_project.data.local.UserLocalDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val uerLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun users(): List<User>? =
        withContext(Dispatchers.IO) {
            // Get from database first
            val dbUsers = uerLocalDataSource.getUsers()
            if (dbUsers.isNullOrEmpty()) {
                // If not found, get from server
                userRemoteDataSource.users()?.map {
                    it.toUser()
                }?.also { userList ->
                    val entities = userList.map {
                        it.toUserEntity()
                    }
                    uerLocalDataSource.insertUsers(entities)
                }
            } else {
                dbUsers.map { it.toUser() }
            }
        }

    override suspend fun user(userId: Int): User? =
        withContext(Dispatchers.IO) {
            // Get from database first
            uerLocalDataSource.getUser(userId)?.toUser()
            // If not found, get from server
                ?: userRemoteDataSource.user(userId)?.toUser()?.also { user ->
                    uerLocalDataSource.insertUsers(listOf(user.toUserEntity()))
                }
        }
}
