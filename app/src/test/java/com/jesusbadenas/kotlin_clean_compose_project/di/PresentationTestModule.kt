package com.jesusbadenas.kotlin_clean_compose_project.di

import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase
import io.mockk.mockk
import org.koin.dsl.module

val presentationTestModule = module {
    factory { mockk<GetUserUseCase>() }
    factory { mockk<GetUsersUseCase>() }
}
