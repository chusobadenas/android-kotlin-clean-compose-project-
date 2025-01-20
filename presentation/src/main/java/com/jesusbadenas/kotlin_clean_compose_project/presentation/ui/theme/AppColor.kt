package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Common colors
val error = Color(0xFFFF5555)
val secondary = Color(0xFF03DAC5)

// Light scheme
val backgroundLight = Color(0xFFF5F5F5)
val primaryLight = Color(0xFF6200EA)
val surfaceLight = Color(0xFFFAFAFA)

// Dark scheme
val backgroundDark = Color(0xFF121212)
val primaryDark = Color(0xFFBB86FC)
val surfaceDark = Color(0xFF1E1E1E)

private val lightScheme = lightColorScheme(
    background = backgroundLight,
    onBackground = Color.Black,
    error = Color.Red,
    onError = Color.White,
    primary = primaryLight,
    onPrimary = Color.White,
    secondary = secondary,
    onSecondary = Color.Black,
    surface = surfaceLight,
    onSurface = Color.Black
)

private val darkScheme = darkColorScheme(
    background = backgroundDark,
    onBackground = Color.White,
    error = Color.Red,
    onError = Color.Black,
    primary = primaryDark,
    onPrimary = Color.Black,
    secondary = secondary,
    onSecondary = Color.Black,
    surface = surfaceDark,
    onSurface = Color.White
)

@Composable
fun selectColorScheme(isDark: Boolean): ColorScheme = if (isDark) {
    darkScheme
} else {
    lightScheme
}
