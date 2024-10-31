package com.jesusbadenas.kotlin_clean_compose_project.di

import com.jesusbadenas.kotlin_clean_compose_project.navigation.Navigator
import com.jesusbadenas.kotlin_clean_compose_project.userdetails.UserDetailsFragment
import com.jesusbadenas.kotlin_clean_compose_project.userlist.UserAdapter
import com.jesusbadenas.kotlin_clean_compose_project.userlist.UserListFragment
import com.jesusbadenas.kotlin_clean_compose_project.viewmodel.MainViewModel
import com.jesusbadenas.kotlin_clean_compose_project.viewmodel.UserDetailsViewModel
import com.jesusbadenas.kotlin_clean_compose_project.viewmodel.UserListViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { UserAdapter() }
    fragment { UserListFragment() }
    fragment { UserDetailsFragment() }
    single { Navigator() }
    viewModel { MainViewModel() }
    viewModel { (id: Int) -> UserDetailsViewModel(id, get()) }
    viewModel { UserListViewModel(get()) }
}
