package com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseViewModel

class UserDetailsViewModel(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun loadUser(userId: Int) {
        getUserUseCase.invoke(
            scope = viewModelScope,
            params = GetUserUseCase.Params(userId),
            onError = { throwable ->
                showError(throwable = throwable, buttonTextId = R.string.btn_text_retry) {
                    onRetryAction()
                }
            },
            onResult = { usr ->
                if (usr == null) {
                    showError(buttonTextId = R.string.btn_text_retry) {
                        onRetryAction()
                    }
                } else {
                    showLoading(false)
                    _user.value = usr
                }
            }
        )
    }
}
