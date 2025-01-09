package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO

interface UserRemoteDataSource {

    suspend fun users(): List<UserDTO>?

    suspend fun user(userId: Int): UserDTO?
}
