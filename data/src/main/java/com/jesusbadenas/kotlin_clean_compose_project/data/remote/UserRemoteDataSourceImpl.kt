package com.jesusbadenas.kotlin_clean_compose_project.data.remote

import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.util.toUser
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import timber.log.Timber

class UserRemoteDataSourceImpl(
    private val usersApi: UsersAPI
): UserRemoteDataSource {

    override suspend fun users(): List<User> {
        val response = runCatching {
            usersApi.users()
        }.getOrElse { exception ->
            Timber.e(exception)
            emptyList()
        }
        return response.map {
            it.toUser()
        }
    }

    override suspend fun user(userId: Int): User? {
        val response = runCatching {
            usersApi.user(userId)
        }.getOrElse { exception ->
            Timber.e(exception)
            null
        }
        return response?.toUser()
    }
}
