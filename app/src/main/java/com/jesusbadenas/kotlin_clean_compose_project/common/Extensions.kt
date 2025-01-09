package com.jesusbadenas.kotlin_clean_compose_project.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.jesusbadenas.kotlin_clean_compose_project.R
import timber.log.Timber

private fun Context.showDialog(
    buttonTextId: Int,
    message: Int,
    title: String,
    action: (() -> Unit)? = null
) {
    // Create dialog
    val builder = AlertDialog.Builder(this)
        .setCancelable(true)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(buttonTextId) { dialog, _ ->
            action?.invoke()
            dialog.dismiss()
        }
    // Show
    builder.create().show()
}

private fun Context.showError(uiError: UIError) {
    // Show log message
    uiError.throwable?.let(Timber::e)
    // Show error dialog
    showDialog(
        buttonTextId = uiError.buttonTextId ?: android.R.string.ok,
        message = uiError.messageTextId ?: R.string.error_message_generic,
        title = getString(R.string.error_title_generic),
        action = uiError.action
    )
}

fun Fragment.showError(uiError: UIError) {
    context?.showError(uiError)
}
