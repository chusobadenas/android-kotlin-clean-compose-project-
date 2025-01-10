package com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    fun loadUserList() {
        getUsersUseCase.invoke()
            .onEach { list ->
                showLoading(false)
                if (list.isEmpty()) {
                    showError(messageTextId = R.string.error_message_empty_list) {
                        onRetryAction()
                    }
                } else {
                    _userList.value = list
                }
            }
            .catch { throwable ->
                showError(throwable = throwable) {
                    onRetryAction()
                }
            }
            .launchIn(viewModelScope)
    }
}
