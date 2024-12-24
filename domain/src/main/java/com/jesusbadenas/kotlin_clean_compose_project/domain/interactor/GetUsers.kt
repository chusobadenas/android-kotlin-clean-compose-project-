package com.jesusbadenas.kotlin_clean_compose_project.domain.interactor

import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository

class GetUsers(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<User> = userRepository.users()
}
