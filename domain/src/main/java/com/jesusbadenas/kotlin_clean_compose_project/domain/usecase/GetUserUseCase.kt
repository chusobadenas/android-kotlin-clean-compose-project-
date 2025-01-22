package com.jesusbadenas.kotlin_clean_compose_project.domain.usecase

import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    private val userRepository: UserRepository
) : UseCaseFlow<GetUserUseCase.Params, User?>() {

    override fun execute(params: Params): Flow<User?> = userRepository.getUser(params.userId)

    data class Params(
        val userId: Int
    )
}
