package com.jesusbadenas.kotlin_clean_compose_project.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _retry = MutableLiveData(false)
    val retry: LiveData<Boolean>
        get() = _retry

    private val _retryAction = MutableLiveEvent<Boolean>()
    val retryAction: LiveDataEvent<Boolean>
        get() = _retryAction

    private val _uiError = MutableLiveData<UIError>()
    val uiError: LiveData<UIError>
        get() = _uiError

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showError(throwable)
    }

    fun showLoading(visible: Boolean) {
        _loading.value = visible
    }

    private fun showRetry(visible: Boolean) {
        _retry.value = visible
    }

    fun onRetryButtonClick() {
        showLoading(true)
        showRetry(false)
        _retryAction.value = LiveEvent(true)
    }

    protected fun showError(throwable: Throwable? = null) {
        showLoading(false)
        showRetry(true)
        _uiError.value = UIError(throwable)
    }
}
