package com.jesusbadenas.kotlin_clean_compose_project.presentation.di

import com.jesusbadenas.kotlin_clean_compose_project.presentation.main.MainViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails.UserDetailsViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist.UserListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }
    viewModel { UserDetailsViewModel(get()) }
    viewModel { UserListViewModel(get()) }
}
