package com.jesusbadenas.kotlin_clean_compose_project.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "cover_url", defaultValue = "''")
    val coverUrl: String = "",
    @ColumnInfo(name = "full_name", defaultValue = "''")
    val fullName: String = "",
    @ColumnInfo(name = "email", defaultValue = "''")
    val email: String = "",
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "followers", defaultValue = "0")
    val followers: Int = 0
) {
    companion object {
        const val TABLE_NAME = "users"
    }
}
