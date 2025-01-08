package com.jesusbadenas.kotlin_clean_compose_project.data.util

import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
    fun `test transform UserResponse to User success`() {
        val userResponse = UserResponse(
            userId = 1,
            coverUrl = "https://localhost/images/1",
            fullName = "John Doe",
            email = "john.doe@example.com",
            description = "",
            followers = 10
        )

        val result = userResponse.toUser()
        Assertions.assertEquals(user, result)
    }

    @Test
    fun `test transform UserEntity to User success`() {
        val userEntity = UserEntity(
            id = 1,
            coverUrl = "https://localhost/images/1",
            fullName = "John Doe",
            email = "john.doe@example.com",
            description = "",
            followers = 10
        )

        val result = userEntity.toUser()
        Assertions.assertEquals(user, result)
    }

    @Test
    fun `test transform User to UserEntity success`() {
        val expected = UserEntity(
            id = 1,
            coverUrl = "https://localhost/images/1",
            fullName = "John Doe",
            email = "john.doe@example.com",
            description = "",
            followers = 10
        )

        val result = user.toUserEntity()
        Assertions.assertEquals(expected, result)
    }
}
