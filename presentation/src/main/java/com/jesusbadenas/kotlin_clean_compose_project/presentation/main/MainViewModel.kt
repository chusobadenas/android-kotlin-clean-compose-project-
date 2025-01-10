package com.jesusbadenas.kotlin_clean_compose_project.presentation.main

import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.LiveDataEvent
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.LiveEvent
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.MutableLiveEvent

class MainViewModel : BaseViewModel() {

    private val _loadAction = MutableLiveEvent<Boolean>()
    val loadAction: LiveDataEvent<Boolean>
        get() = _loadAction

    fun onLoadButtonClick() {
        _loadAction.value = LiveEvent(true)
    }
}
