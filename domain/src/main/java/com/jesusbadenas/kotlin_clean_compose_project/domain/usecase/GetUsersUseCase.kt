package com.jesusbadenas.kotlin_clean_compose_project.domain.usecase

import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val userRepository: UserRepository
) : UseCaseNoParams<Flow<List<User>>>() {

    override suspend fun execute(): Flow<List<User>> = userRepository.getUsers()
}
