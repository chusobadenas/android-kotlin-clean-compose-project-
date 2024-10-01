package com.jesusbadenas.kotlin_clean_compose_project.common

import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val containerVisibility = MutableLiveData<Int>()
    val loadingVisibility = MutableLiveData<Int>()
    val retryVisibility = MutableLiveData<Int>()
    val uiError = MutableLiveData<UIError>()

    val retryAction = LiveEvent<Nothing>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
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
        retryAction.call()
    }

    fun showError(
        throwable: Throwable,
        errorMsgId: Int? = null,
        action: DialogInterface.OnClickListener? = null
    ) {
        showLoading(View.GONE)
        showRetry(View.VISIBLE)
        uiError.value = UIError(throwable, errorMsgId, action)
    }

    fun CoroutineScope.safeLaunch(
        exceptionHandler: CoroutineExceptionHandler = coroutineExceptionHandler,
        body: suspend () -> Unit
    ): Job =
        launch(exceptionHandler) {
            body.invoke()
        }
}
