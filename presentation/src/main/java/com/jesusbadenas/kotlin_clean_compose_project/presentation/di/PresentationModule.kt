package com.jesusbadenas.kotlin_clean_compose_project.presentation.di

import com.jesusbadenas.kotlin_clean_compose_project.presentation.navigation.Navigator
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails.UserDetailsFragment
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails.UserDetailsViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist.UserAdapter
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist.UserListFragment
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist.UserListViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist.UserListener
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single { Navigator() }
    factory { (listener: UserListener) -> UserAdapter(listener) }
    fragment { UserListFragment() }
    fragment { UserDetailsFragment() }
    viewModel { UserDetailsViewModel(get()) }
    viewModel { UserListViewModel(get()) }
}
