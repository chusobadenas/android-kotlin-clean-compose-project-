package com.jesusbadenas.kotlin_clean_compose_project.data.repository

import com.jesusbadenas.kotlin_clean_compose_project.data.local.UserLocalDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.jesusbadenas.kotlin_clean_compose_project.domain.util.toList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun getUsers(): Flow<List<User>> = flow {
        // Get from database first
        val localUsers = userLocalDataSource.getUsers().firstOrNull()
        if (localUsers.isNullOrEmpty()) {
            // If not found, get from server
            val remoteUsers = userRemoteDataSource.getUsers().firstOrNull()
                ?.map { it.toUser() }
                .orEmpty()
            val entities = remoteUsers.map { it.toUserEntity() }
            userLocalDataSource.insertUsers(entities)
            emit(remoteUsers)
        } else {
            // Emit database users
            emit(localUsers.map { it.toUser() })
        }
    }

    override fun getUser(userId: Int): Flow<User?> = flow {
        // Get from database first
        val localUser = userLocalDataSource.getUser(userId).firstOrNull()
        if (localUser == null) {
            // If not found, get from server
            val remoteUser = userRemoteDataSource.getUser(userId).firstOrNull()
                ?.toUser()
            val entity = remoteUser?.toUserEntity()
            userLocalDataSource.insertUsers(entity.toList().mapNotNull { it })
            emit(remoteUser)
        } else {
            // Emit database user
            emit(localUser.toUser())
        }
    }
}
