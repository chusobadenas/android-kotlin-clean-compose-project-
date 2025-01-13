package com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIError
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.LiveDataEvent
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.LiveEvent
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.MutableLiveEvent

class UserDetailsViewModel(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _retryAction = MutableLiveEvent<Boolean>()
    val retryAction: LiveDataEvent<Boolean>
        get() = _retryAction

    private val _uiError = MutableLiveData<UIError>()
    val uiError: LiveData<UIError>
        get() = _uiError

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun loadUser(userId: Int) {
        getUserUseCase.invoke(
            scope = viewModelScope,
            params = GetUserUseCase.Params(userId),
            onError = { throwable ->
                showError(throwable = throwable)
            },
            onResult = { usr ->
                usr?.let(::showUser) ?: showError()
            }
        )
    }

    private fun doRetry() {
        showLoading(true)
        _retryAction.value = LiveEvent(true)
    }

    private fun showError(throwable: Throwable? = null) {
        showLoading(false)
        _uiError.value = UIError(throwable = throwable) {
            doRetry()
        }
    }

    private fun showUser(user: User) {
        showLoading(false)
        _user.value = user
    }
}
