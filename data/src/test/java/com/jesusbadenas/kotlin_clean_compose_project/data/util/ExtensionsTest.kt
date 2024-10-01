package com.jesusbadenas.kotlin_clean_compose_project.data.util

import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import org.junit.Assert
import org.junit.Test

class ExtensionsTest {

    private val user = User(
        userId = 1,
        coverUrl = "https://localhost/images/1",
        fullName = "John Doe",
        email = "john.doe@example.com",
        description = "",
        followers = 10
    )

    @Test
    fun testUserResponseConversionSuccess() {
        val userResponse = UserResponse(
            userId = 1,
            coverUrl = "https://localhost/images/1",
            fullName = "John Doe",
            email = "john.doe@example.com",
            description = "",
            followers = 10
        )

        val result = userResponse.toUser()
        Assert.assertEquals(user, result)
    }

    @Test
    fun testUserEntityConversionSuccess() {
        val userEntity = UserEntity(
            id = 1,
            coverUrl = "https://localhost/images/1",
            fullName = "John Doe",
            email = "john.doe@example.com",
            description = "",
            followers = 10
        )

        val result = userEntity.toUser()
        Assert.assertEquals(user, result)
    }

    @Test
    fun testUserConversionSuccess() {
        val expected = UserEntity(
            id = 1,
            coverUrl = "https://localhost/images/1",
            fullName = "John Doe",
            email = "john.doe@example.com",
            description = "",
            followers = 10
        )

        val result = user.toUserEntity()
        Assert.assertEquals(expected, result)
    }
}
