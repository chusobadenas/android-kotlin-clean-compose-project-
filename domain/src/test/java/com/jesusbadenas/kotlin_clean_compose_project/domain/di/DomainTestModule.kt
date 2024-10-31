package com.jesusbadenas.kotlin_clean_compose_project.domain.di

import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import io.mockk.mockk
import org.koin.dsl.module

val domainTestModule = module {
    factory { mockk<UserRepository>() }
}
