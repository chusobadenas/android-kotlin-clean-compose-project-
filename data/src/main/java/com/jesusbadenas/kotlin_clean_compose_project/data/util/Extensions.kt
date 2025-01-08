package com.jesusbadenas.kotlin_clean_compose_project.data.util

import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User

fun UserResponse.toUser() = User(
    userId = userId,
    coverUrl = coverUrl,
    fullName = fullName,
    email = email,
    description = description,
    followers = followers
)

fun UserEntity.toUser() = User(
    userId = id,
    coverUrl = coverUrl,
    fullName = fullName,
    email = email,
    description = description,
    followers = followers
)

fun User.toUserEntity() = UserEntity(
    id = userId,
    coverUrl = coverUrl.orEmpty(),
    fullName = fullName.orEmpty(),
    email = email.orEmpty(),
    description = description,
    followers = followers ?: 0
)
