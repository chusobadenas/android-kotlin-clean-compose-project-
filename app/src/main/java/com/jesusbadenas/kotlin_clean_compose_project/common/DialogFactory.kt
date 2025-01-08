package com.jesusbadenas.kotlin_clean_compose_project.common

import android.content.Context
import android.content.DialogInterface
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.jesusbadenas.kotlin_clean_compose_project.R

/**
 * Class for creating dialogs
 */
object DialogFactory {

    enum class DialogType {
        CONFIRM, PROGRESS, SIMPLE
    }

    private fun createConfirmDialog(
        context: Context, title: String?, message: String?,
        buttonTextId: Int, action: DialogInterface.OnClickListener?
    ): AlertDialog {
        // Create dialog
        val builder = AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(buttonTextId, action)

        // Return the dialog
        return builder.create()
    }

    private fun createProgressDialog(context: Context): AlertDialog {
        // Create dialog
        val builder = AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(R.layout.loading_progress_view)

        return builder.create()
    }

    private fun createSimpleDialog(
        context: Context, title: String?, message: String?,
        action: DialogInterface.OnClickListener?
    ): AlertDialog {
        // Create dialog
        val builder = AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(android.R.string.ok, action)

        // Return the dialog
        return builder.create()
    }

    fun createListDialog(
        context: Context, title: String?, items: List<String>,
        listener: DialogInterface.OnClickListener
    ): AlertDialog {
        // Create adapter
        val adapter = ArrayAdapter(context, R.layout.list_item_single_choice, items)

        // Create dialog
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setAdapter(adapter, listener)

        // Return the dialog
        return builder.create()
    }

    private fun setButtonColor(context: Context, dialog: AlertDialog, buttonType: Int) {
        // Get color accent
        val color = ContextCompat.getColor(context, R.color.accent)
        // Set button color
        val button = dialog.getButton(buttonType)
        button?.setTextColor(color)
    }

    fun showDialog(
        context: Context,
        type: DialogType,
        title: String?,
        message: String?,
        buttonTextId: Int,
        action: DialogInterface.OnClickListener? = null
    ): AlertDialog {
        // Create dialog
        val dialog = when (type) {
            DialogType.CONFIRM -> createConfirmDialog(context, title, message, buttonTextId, action)
            DialogType.PROGRESS -> createProgressDialog(context)
            DialogType.SIMPLE -> createSimpleDialog(context, title, message, action)
        }

        // Show dialog
        dialog.show()
        // Change buttons color
        setButtonColor(context, dialog, DialogInterface.BUTTON_NEUTRAL)
        setButtonColor(context, dialog, DialogInterface.BUTTON_POSITIVE)

        return dialog
    }
}
