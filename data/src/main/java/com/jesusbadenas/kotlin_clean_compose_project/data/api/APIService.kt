package com.jesusbadenas.kotlin_clean_compose_project.data.api

import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * APIService for retrieving data from the network using Retrofit2
 */
interface APIService {

    @GET("/users.json")
    suspend fun userDataList(): List<UserResponse>

    @GET("/user_{$USER_ID}.json")
    suspend fun userDataById(@Path(USER_ID) userId: Int): UserResponse

    companion object {
        const val API_BASE_URL = "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/"
        private const val USER_ID = "userId"
    }
}
