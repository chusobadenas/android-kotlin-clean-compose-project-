package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUsers
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User

class UserListViewModel(private val getUsersUseCase: GetUsers) : BaseViewModel() {

    val userList = MutableLiveData<List<User>>()

    init {
        loadUserList()
    }

    fun loadUserList() {
        viewModelScope.safeLaunch {
            userList.value = getUsersUseCase()
        }
    }
}
