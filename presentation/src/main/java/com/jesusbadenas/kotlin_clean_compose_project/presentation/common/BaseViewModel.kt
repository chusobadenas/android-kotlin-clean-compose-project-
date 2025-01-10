package com.jesusbadenas.kotlin_clean_compose_project.presentation.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIError
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.LiveDataEvent
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.LiveEvent
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.MutableLiveEvent

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _retryAction = MutableLiveEvent<Boolean>()
    val retryAction: LiveDataEvent<Boolean>
        get() = _retryAction

    private val _uiError = MutableLiveData<UIError>()
    val uiError: LiveData<UIError>
        get() = _uiError

    fun showLoading(visible: Boolean) {
        _loading.value = visible
    }

    protected fun onRetryAction() {
        showLoading(true)
        _retryAction.value = LiveEvent(true)
    }

    protected fun showError(
        throwable: Throwable? = null,
        messageTextId: Int? = null,
        buttonTextId: Int? = null,
        action: (() -> Unit)? = null
    ) {
        showLoading(false)
        _uiError.value = UIError(throwable, messageTextId, buttonTextId, action)
    }
}
