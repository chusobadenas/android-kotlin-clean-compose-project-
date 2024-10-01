package com.jesusbadenas.kotlin_clean_compose_project.data.api.response

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UserResponse(
    @field:Json(name = "id") val userId: Int,
    @field:Json(name = "cover_url") var coverUrl: String? = null,
    @field:Json(name = "full_name") var fullName: String? = null,
    @field:Json(name = "email") var email: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "followers") var followers: Int? = 0
)
