package com.jesusbadenas.kotlin_clean_compose_project.data.local

import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import timber.log.Timber

class UserLocalDataSourceImpl(
    private val usersDao: UserDao
) : UserLocalDataSource {

    override suspend fun getUsers(): List<UserEntity>? {
        return runCatching {
            usersDao.getAll()
        }.getOrElse { exception ->
            Timber.e(exception)
            null
        }
    }

    override suspend fun getUser(userId: Int): UserEntity? {
        return runCatching {
            usersDao.findById(userId)
        }.getOrElse { exception ->
            Timber.e(exception)
            null
        }
    }

    override suspend fun insertUsers(users: List<UserEntity>) {
        runCatching {
            usersDao.insert(*users.toTypedArray())
        }.getOrElse { exception ->
            Timber.e(exception)
        }
    }
}
