package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {

    suspend fun getUsers(): Flow<List<UserDTO>>

    suspend fun getUser(userId: Int): UserDTO?
}
