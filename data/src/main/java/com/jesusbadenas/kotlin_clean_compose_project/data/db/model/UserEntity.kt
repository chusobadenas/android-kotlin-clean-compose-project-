package com.jesusbadenas.kotlin_clean_compose_project.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "email", defaultValue = "''")
    val email: String = "",
    @ColumnInfo(name = "image_url", defaultValue = "''")
    val imageUrl: String = "",
    @ColumnInfo(name = "name", defaultValue = "''")
    val name: String = "",
    @ColumnInfo(name = "website")
    val website: String? = null
) {
    companion object {
        const val TABLE_NAME = "users"
    }
}
