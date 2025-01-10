package com.jesusbadenas.kotlin_clean_compose_project.presentation.model

import com.jesusbadenas.kotlin_clean_compose_project.presentation.R

data class UIError(
    val throwable: Throwable? = null,
    val messageTextId: Int? = R.string.error_message_generic,
    val buttonTextId: Int? = R.string.btn_text_retry,
    val action: (() -> Unit)? = null
)
