package com.jesusbadenas.kotlin_clean_compose_project.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase

class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    fun loadUserList() {
        getUsersUseCase.invoke(
            scope = viewModelScope,
            coroutineExceptionHandler = coroutineExceptionHandler
        ) { list ->
            _userList.value = list
        }
    }
}
