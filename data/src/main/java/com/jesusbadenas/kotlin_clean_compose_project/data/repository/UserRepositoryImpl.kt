package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import com.jesusbadenas.kotlin_clean_compose_project.data.local.UserLocalDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toList
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUsers(): Flow<List<User>> = flow {
        // Get from database first
        val localUsers = userLocalDataSource.getUsers().firstOrNull()
        if (localUsers.isNullOrEmpty()) {
            // If not found, get from server
            val remoteUsers = userRemoteDataSource.getUsers().firstOrNull()?.map { it.toUser() }.orEmpty()
            val entities = remoteUsers.map { it.toUserEntity() }
            userLocalDataSource.insertUsers(entities)
            emit(remoteUsers)
        } else {
            emit(localUsers.map { it.toUser() })
        }
    }

    override suspend fun getUser(userId: Int): User? = withContext(Dispatchers.IO) {
            // Get from database first
            userLocalDataSource.getUser(userId)?.toUser()
            // If not found, get from server
                ?: userRemoteDataSource.getUser(userId)?.toUser()?.also { user ->
                    userLocalDataSource.insertUsers(user.toUserEntity().toList())
                }
        }
}
