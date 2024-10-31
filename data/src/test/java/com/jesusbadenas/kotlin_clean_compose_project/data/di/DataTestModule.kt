package com.jesusbadenas.kotlin_clean_compose_project.data.di

import com.jesusbadenas.kotlin_clean_compose_project.data.api.APIService
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import io.mockk.mockk
import org.koin.dsl.module

val dataTestModule = module {
    factory { mockk<APIService>() }
    factory { mockk<UserRepository>() }
}
