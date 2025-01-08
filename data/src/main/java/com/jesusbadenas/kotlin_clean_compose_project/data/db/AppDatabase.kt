package com.jesusbadenas.kotlin_clean_compose_project.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.db.dao.UserDao
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = DBConstants.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
