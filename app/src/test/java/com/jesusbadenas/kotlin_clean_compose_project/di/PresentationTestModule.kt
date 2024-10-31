package com.jesusbadenas.kotlin_clean_compose_project.di

import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUser
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUsers
import io.mockk.mockk
import org.koin.dsl.module

val presentationTestModule = module {
    factory { mockk<GetUser>() }
    factory { mockk<GetUsers>() }
}
