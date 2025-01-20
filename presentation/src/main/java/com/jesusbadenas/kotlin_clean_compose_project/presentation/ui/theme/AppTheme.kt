package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    StatusBar(darkTheme)
    MaterialTheme(
        colorScheme = selectColorScheme(darkTheme),
        typography = appTypography,
        content = content
    )
}

@Composable
fun StatusBar(
    darkTheme: Boolean
) {
    val context = LocalContext.current
    val view = LocalView.current
    SideEffect {
        (context as? Activity)?.window?.let { window ->
            WindowCompat.setDecorFitsSystemWindows(window, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    window.insetsController?.apply {
                        show(WindowInsetsCompat.Type.statusBars())
                        systemBarsBehavior = WindowInsetsController.BEHAVIOR_DEFAULT
                        setSystemBarsAppearance(
                            0,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        )
                    }
                }
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }
}
