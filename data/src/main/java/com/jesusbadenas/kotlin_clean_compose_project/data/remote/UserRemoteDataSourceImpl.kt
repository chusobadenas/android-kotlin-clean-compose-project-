package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.api.model.UserDTO
import timber.log.Timber

class UserRemoteDataSourceImpl(
    private val usersApi: UsersAPI
) : UserRemoteDataSource {

    override suspend fun users(): List<UserDTO>? {
        return runCatching {
            usersApi.users()
        }.getOrElse { exception ->
            Timber.e(exception)
            null
        }
    }

    override suspend fun user(userId: Int): UserDTO? {
        return runCatching {
            usersApi.user(userId)
        }.getOrElse { exception ->
            Timber.e(exception)
            null
        }
    }
}
