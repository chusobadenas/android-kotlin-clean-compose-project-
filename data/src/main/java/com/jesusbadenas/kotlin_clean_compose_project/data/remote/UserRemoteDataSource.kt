package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User

interface UserRemoteDataSource {

    suspend fun users(): List<User>?

    suspend fun user(userId: Int): User?
}
