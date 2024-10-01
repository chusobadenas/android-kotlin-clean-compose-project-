package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import com.jesusbadenas.kotlin_clean_compose_project.common.BaseViewModel
import com.jesusbadenas.kotlin_clean_compose_project.common.LiveEvent

class MainViewModel: BaseViewModel() {

    val loadAction = LiveEvent<Void>()

    fun onLoadButtonClick() {
        loadAction.call()
    }
}
