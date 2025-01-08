package com.jesusbadenas.kotlin_clean_compose_project.data.api

import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * APIService for retrieving user data from the network using Retrofit2
 */
interface UsersAPI {

    @GET("/users.json")
    suspend fun users(): List<UserResponse>

    @GET("/user_{$USER_ID}.json")
    suspend fun user(
        @Path(USER_ID) userId: Int
    ): UserResponse

    companion object {
        const val API_BASE_URL = "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/"
        private const val USER_ID = "userId"
    }
}
