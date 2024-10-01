package com.jesusbadenas.kotlin_clean_compose_project.di

import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.di.domainModule

val appComponent = listOf(
    dataModule,
    domainModule,
    appModule
)
