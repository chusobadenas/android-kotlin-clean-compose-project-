package com.jesusbadenas.kotlin_clean_compose_project.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): UserEntity?

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg users: UserEntity)
}
