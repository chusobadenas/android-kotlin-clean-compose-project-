package com.jesusbadenas.kotlin_clean_compose_project.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.common.UIError
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase

class UserDetailsViewModel(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun loadUser(userId: Int) {
        getUserUseCase.invoke(
            scope = viewModelScope,
            coroutineExceptionHandler = coroutineExceptionHandler,
            params = GetUserUseCase.Params(userId)
        ) { usr ->
            usr?.let {
                showLoading(false)
                _user.value = it
            } ?: run {
                showError(buttonTextId = R.string.btn_text_retry) {
                    onRetryAction()
                }
            }
        }
    }
}
