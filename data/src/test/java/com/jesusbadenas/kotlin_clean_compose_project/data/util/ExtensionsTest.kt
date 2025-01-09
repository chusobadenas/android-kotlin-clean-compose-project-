package com.jesusbadenas.kotlin_clean_compose_project.data.util

import com.jesusbadenas.kotlin_clean_compose_project.data.api.response.UserResponse
import com.jesusbadenas.kotlin_clean_compose_project.data.db.model.UserEntity
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExtensionsTest {

    private val user = User(
        id = 1,
        email = "john.doe@example.com",
        imageUrl = "https://thispersondoesnotexist.com/",
        name = "John Doe",
        website = ""
    )

    @Test
    fun `test transform UserResponse to User success`() {
        val userResponse = UserResponse(
            id = 1,
            email = "john.doe@example.com",
            name = "John Doe",
            website = ""
        )

        val result = userResponse.toUser()
        Assertions.assertEquals(user, result)
    }

    @Test
    fun `test transform UserEntity to User success`() {
        val userEntity = UserEntity(
            id = 1,
            email = "john.doe@example.com",
            imageUrl = "https://thispersondoesnotexist.com/",
            name = "John Doe",
            website = ""
        )

        val result = userEntity.toUser()
        Assertions.assertEquals(user, result)
    }

    @Test
    fun `test transform User to UserEntity success`() {
        val expected = UserEntity(
            id = 1,
            email = "john.doe@example.com",
            imageUrl = "https://thispersondoesnotexist.com/",
            name = "John Doe",
            website = ""
        )

        val result = user.toUserEntity()
        Assertions.assertEquals(expected, result)
    }
}
