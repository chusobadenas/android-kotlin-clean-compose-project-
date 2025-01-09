package com.jesusbadenas.kotlin_clean_compose_project.di

import com.jesusbadenas.kotlin_clean_compose_project.main.MainViewModel
import com.jesusbadenas.kotlin_clean_compose_project.navigation.Navigator
import com.jesusbadenas.kotlin_clean_compose_project.userdetails.UserDetailsFragment
import com.jesusbadenas.kotlin_clean_compose_project.userdetails.UserDetailsViewModel
import com.jesusbadenas.kotlin_clean_compose_project.userlist.UserAdapter
import com.jesusbadenas.kotlin_clean_compose_project.userlist.UserListFragment
import com.jesusbadenas.kotlin_clean_compose_project.userlist.UserListViewModel
import com.jesusbadenas.kotlin_clean_compose_project.userlist.UserListener
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Navigator() }
    factory { (listener: UserListener) -> UserAdapter(listener) }
    fragment { UserListFragment() }
    fragment { UserDetailsFragment() }
    viewModel { MainViewModel() }
    viewModel { UserDetailsViewModel(get()) }
    viewModel { UserListViewModel(get()) }
}
