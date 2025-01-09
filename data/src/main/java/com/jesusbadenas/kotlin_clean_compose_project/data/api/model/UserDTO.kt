package com.jesusbadenas.kotlin_clean_compose_project.data.api.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UserDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "website")
    val website: String? = null
)
