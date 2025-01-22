package com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails

import androidx.lifecycle.viewModelScope
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.domain.usecase.GetUserUseCase
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<UIState<User>>(UIState.Loading)
    val uiState: StateFlow<UIState<User>>
        get() = _uiState.asStateFlow()

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(params = GetUserUseCase.Params(userId))
                .catch { throwable ->
                    _uiState.value = UIState.Error(throwable)
                }
                .collect { user ->
                    _uiState.value = UIState.Success(user)
                }
        }
    }
}
