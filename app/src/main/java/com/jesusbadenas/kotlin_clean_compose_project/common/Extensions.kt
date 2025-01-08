package com.jesusbadenas.kotlin_clean_compose_project.common

import android.content.Context
import androidx.fragment.app.Fragment
import com.jesusbadenas.kotlin_clean_compose_project.R
import timber.log.Timber

fun Context.showError(uiError: UIError) {
    // Show log message
    Timber.e(uiError.throwable)

    // Show dialog
    val message = getString(R.string.error_message_generic)
    val title = getString(R.string.error_title_generic)

    DialogFactory.showDialog(
        context = this,
        type = DialogFactory.DialogType.SIMPLE,
        title = title,
        message = message,
        buttonTextId = android.R.string.ok
    )
}

fun Fragment.showError(uiError: UIError) {
    context?.showError(uiError)
}
