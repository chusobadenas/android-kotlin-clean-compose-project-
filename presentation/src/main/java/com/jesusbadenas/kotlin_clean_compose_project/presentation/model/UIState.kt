package com.jesusbadenas.kotlin_clean_compose_project.presentation.model

sealed class UIState<out T> {
    data object Empty : UIState<Nothing>()

    data class Error<T>(val exception: Throwable? = null, val data: T? = null) : UIState<T>()

    data object Loading : UIState<Nothing>()

    data class Success<out T>(val data: T? = null) : UIState<T>()
}
