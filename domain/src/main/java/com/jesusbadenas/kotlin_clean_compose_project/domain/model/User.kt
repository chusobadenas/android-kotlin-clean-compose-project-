package com.jesusbadenas.kotlin_clean_compose_project.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class User(
    val id: Int,
    val email: String? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val website: String? = null
)
