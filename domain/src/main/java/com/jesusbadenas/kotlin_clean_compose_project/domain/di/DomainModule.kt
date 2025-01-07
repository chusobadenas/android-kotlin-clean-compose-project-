package com.jesusbadenas.kotlin_clean_compose_project.domain.di

import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetUserUseCase(get()) }
    factory { GetUsersUseCase(get()) }
}
