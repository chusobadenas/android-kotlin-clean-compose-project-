package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val error = Color(0xFFFF5555)
val onPrimaryDark = Color(0xFF213600)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryLight = Color(0xFF476810)
val primaryLightContainer = Color(0xFFC7F089)
val primaryDark = Color(0xFFACD370)
val primaryDarkContainer = Color(0xFF324F00)

private val lightScheme = lightColorScheme(
    error = error,
    onPrimary = onPrimaryLight,
    primary = primaryLight,
    primaryContainer = primaryLightContainer
)

private val darkScheme = darkColorScheme(
    error = error,
    onPrimary = onPrimaryDark,
    primary = primaryDark,
    primaryContainer = primaryDarkContainer
)

@Composable
fun selectColorScheme(isDark: Boolean): ColorScheme = if (isDark) {
    darkScheme
} else {
    lightScheme
}
