package com.jesusbadenas.kotlin_clean_compose_project.data.util

import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User

fun UserResponse.toUser() = User(
    id = id,
    email = email,
    imageUrl = "https://thispersondoesnotexist.com/",
    name = name,
    website = website
)

fun UserEntity.toUser() = User(
    id = id,
    email = email,
    imageUrl = imageUrl,
    name = name,
    website = website
)

fun User.toUserEntity() = UserEntity(
    id = id,
    email = email.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    name = name.orEmpty(),
    website = website
)
