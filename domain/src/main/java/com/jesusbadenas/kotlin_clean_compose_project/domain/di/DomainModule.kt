package com.jesusbadenas.kotlin_clean_compose_project.domain.di

import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUser
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUsers
import org.koin.dsl.module

val domainModule = module {
    factory { GetUser(get()) }
    factory { GetUsers(get()) }
}
