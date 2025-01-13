package com.jesusbadenas.kotlin_clean_compose_project.presentation.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun showLoading(visible: Boolean) {
        _loading.value = visible
    }
}
