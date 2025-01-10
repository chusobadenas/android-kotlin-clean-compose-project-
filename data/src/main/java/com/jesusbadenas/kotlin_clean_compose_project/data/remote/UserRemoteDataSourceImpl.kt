package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO
import kotlinx.coroutines.flow.Flow

class UserRemoteDataSourceImpl(
    private val usersApi: UsersAPI
) : UserRemoteDataSource {

    override suspend fun getUsers(): Flow<List<UserDTO>> = usersApi.users()

    override suspend fun getUser(userId: Int): UserDTO? = usersApi.user(userId)
}
