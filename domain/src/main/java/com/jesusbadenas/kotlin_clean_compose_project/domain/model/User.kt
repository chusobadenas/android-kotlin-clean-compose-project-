package com.jesusbadenas.kotlin_clean_compose_project.domain.model

import androidx.annotation.Keep

@Keep
data class User(
    val userId: Int,
    val coverUrl: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val description: String? = null,
    val followers: Int? = 0
)
