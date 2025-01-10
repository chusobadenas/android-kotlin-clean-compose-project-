package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRemoteDataSourceImpl(
    private val usersApi: UsersAPI
) : UserRemoteDataSource {

    override suspend fun getUsers(): Flow<List<UserDTO>> = flowOf(usersApi.users())

    override suspend fun getUser(userId: Int): UserDTO? = usersApi.user(userId)
}
