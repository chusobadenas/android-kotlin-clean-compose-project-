package com.jesusbadenas.kotlin_clean_compose_project.main

import com.jesusbadenas.kotlin_clean_compose_project.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.common.LiveDataEvent
import com.jesusbadenas.kotlin_clean_compose_project.common.LiveEvent
import com.jesusbadenas.kotlin_clean_compose_project.common.MutableLiveEvent

class MainViewModel : BaseViewModel() {

    private val _loadAction = MutableLiveEvent<Boolean>()
    val loadAction: LiveDataEvent<Boolean>
        get() = _loadAction

    fun onLoadButtonClick() {
        _loadAction.value = LiveEvent(true)
    }
}
