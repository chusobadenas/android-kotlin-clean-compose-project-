package com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist

import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUsersUseCase
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<User>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<User>>>
        get() = _uiState.asStateFlow()

    fun loadUserList() {
        viewModelScope.launch {
            getUsersUseCase.invoke()
                .catch { throwable ->
                    _uiState.value = UIState.Error(throwable)
                }
                .collect { list ->
                    if (list.isEmpty()) {
                        _uiState.value = UIState.Empty
                    } else {
                        _uiState.value = UIState.Success(list)
                    }
                }
        }
    }
}
