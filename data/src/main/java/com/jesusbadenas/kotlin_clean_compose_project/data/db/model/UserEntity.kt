package com.jesusbadenas.kotlin_clean_compose_project.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "cover_url")
    val coverUrl: String? = null,
    @ColumnInfo(name = "full_name")
    val fullName: String? = null,
    @ColumnInfo(name = "email")
    val email: String? = null,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "followers")
    val followers: Int? = 0
) {
    companion object {
        const val TABLE_NAME = "users"
    }
}
