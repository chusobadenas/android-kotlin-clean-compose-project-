package com.jesusbadenas.kotlin_clean_compose_project.domain.model

import androidx.annotation.Keep

@Keep
data class User(
    val userId: Int,
    var coverUrl: String? = null,
    var fullName: String? = null,
    var email: String? = null,
    var description: String? = null,
    var followers: Int? = 0
)
