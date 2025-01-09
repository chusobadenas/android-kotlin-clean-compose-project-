package com.jesusbadenas.kotlin_clean_compose_project.data.api.response

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UserResponse(
    @Json(name = "id")
    val userId: Int,
    @Json(name = "cover_url")
    val coverUrl: String? = null,
    @Json(name = "full_name")
    val fullName: String? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "followers")
    val followers: Int? = 0
)
