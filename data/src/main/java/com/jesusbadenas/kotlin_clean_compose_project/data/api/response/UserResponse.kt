package com.jesusbadenas.kotlin_clean_compose_project.data.api.response

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UserResponse(
    @field:Json(name = "id")
    val userId: Int,
    @field:Json(name = "cover_url")
    val coverUrl: String? = null,
    @field:Json(name = "full_name")
    val fullName: String? = null,
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "followers")
    val followers: Int? = 0
)
