package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRemoteDataSourceImpl(
    private val usersApi: UsersAPI
) : UserRemoteDataSource {

    override fun getUsers(): Flow<List<UserDTO>> = flow {
        emit(usersApi.users())
    }

    override fun getUser(userId: Int): Flow<UserDTO?> = flow {
        emit(usersApi.user(userId))
    }
}
