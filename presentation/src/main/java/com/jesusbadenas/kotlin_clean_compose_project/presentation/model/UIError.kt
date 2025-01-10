package com.jesusbadenas.kotlin_clean_compose_project.presentation.model

data class UIError(
    val throwable: Throwable? = null,
    val messageTextId: Int? = null,
    val buttonTextId: Int? = null,
    val action: (() -> Unit)? = null
)
