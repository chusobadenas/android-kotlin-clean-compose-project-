package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.domain.interactor.GetUser
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User

class UserDetailsViewModel(
    private val userId: Int,
    private val getUserUseCase: GetUser
) : BaseViewModel() {

    val user = MutableLiveData<User>()

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.safeLaunch {
            user.value = getUserUseCase(userId)
        }
    }
}
