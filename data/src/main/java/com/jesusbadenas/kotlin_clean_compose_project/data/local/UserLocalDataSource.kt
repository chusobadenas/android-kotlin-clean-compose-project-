package com.jesusbadenas.kotlin_clean_compose_project.data.local

import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun getUsers(): Flow<List<UserEntity>>

    suspend fun getUser(userId: Int): UserEntity?

    suspend fun insertUsers(users: List<UserEntity>)
}
