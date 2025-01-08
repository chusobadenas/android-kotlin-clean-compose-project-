package com.jesusbadenas.kotlin_clean_compose_project.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _retryAction = MutableLiveEvent<Boolean>()
    val retryAction: LiveDataEvent<Boolean>
        get() = _retryAction

    private val _uiError = MutableLiveData<UIError>()
    val uiError: LiveData<UIError>
        get() = _uiError

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showError(throwable)
    }

    init {
        showLoading(true)
    }

    fun showLoading(visible: Boolean) {
        _loading.value = visible
    }

    fun onRetryButtonClick() {
        showLoading(true)
        _retryAction.value = LiveEvent(true)
    }

    protected fun showError(throwable: Throwable? = null) {
        showLoading(false)
        _uiError.value = UIError(throwable)
    }
}
