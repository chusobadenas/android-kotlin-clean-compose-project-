package com.jesusbadenas.kotlin_clean_compose_project.common

import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    val containerVisibility = MutableLiveData<Int>()
    val loadingVisibility = MutableLiveData<Int>()
    val retryVisibility = MutableLiveData<Int>()
    val uiError = MutableLiveData<UIError>()

    private val _retryAction = MutableLiveEvent<Boolean>()
    val retryAction: LiveDataEvent<Boolean>
        get() = _retryAction

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showError(throwable)
    }

    init {
        showLoading(View.VISIBLE)
        showRetry(View.GONE)
    }

    fun showLoading(loadingVisibility: Int) {
        this.loadingVisibility.value = loadingVisibility
        this.containerVisibility.value =
            if (loadingVisibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    fun showRetry(retryVisibility: Int) {
        this.retryVisibility.value = retryVisibility
        this.containerVisibility.value =
            if (retryVisibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    fun onRetryButtonClick() {
        showRetry(View.GONE)
        showLoading(View.VISIBLE)
        _retryAction.value = LiveEvent(true)
    }

    private fun showError(
        throwable: Throwable,
        errorMsgId: Int? = null,
        action: DialogInterface.OnClickListener? = null
    ) {
        showLoading(View.GONE)
        showRetry(View.VISIBLE)
        uiError.value = UIError(throwable, errorMsgId, action)
    }
}
