package com.jesusbadenas.kotlin_clean_compose_project.data.local

import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    fun getUsers(): Flow<List<UserEntity>>

    fun getUser(userId: Int): Flow<UserEntity?>

    suspend fun insertUsers(users: List<UserEntity>)
}
