package com.jesusbadenas.kotlin_clean_compose_project.domain.repository

import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<List<User>>

    fun getUser(userId: Int): Flow<User?>
}
