package com.jesusbadenas.kotlin_clean_compose_project.data.local

import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import kotlinx.coroutines.flow.Flow

class UserLocalDataSourceImpl(
    private val usersDao: UserDao
) : UserLocalDataSource {

    override suspend fun getUsers(): Flow<List<UserEntity>> = usersDao.getAll()

    override suspend fun getUser(userId: Int): UserEntity? = usersDao.getById(userId)

    override suspend fun insertUsers(users: List<UserEntity>) {
        usersDao.insert(users)
    }
}
