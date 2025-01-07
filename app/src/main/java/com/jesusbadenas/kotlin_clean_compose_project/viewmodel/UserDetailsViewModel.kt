package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase

class UserDetailsViewModel(
    private val userId: Int,
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun loadUser() {
        getUserUseCase.invoke(
            scope = viewModelScope,
            coroutineExceptionHandler = coroutineExceptionHandler,
            params = GetUserUseCase.Params(userId)
        ) { usr ->
            _user.value = usr
        }
    }
}
