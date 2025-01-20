package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen

import androidx.compose.runtime.Composable
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.Toolbar

@Composable
fun MainScreen(
    onNavigateToUsersList: () -> Unit
) {
    Toolbar()
}
