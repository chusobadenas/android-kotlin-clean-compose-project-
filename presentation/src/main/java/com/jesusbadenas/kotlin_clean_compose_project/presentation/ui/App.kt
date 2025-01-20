package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.navigation.AppNavHost

@Composable
fun App() {
    val navController = rememberNavController()
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        AppNavHost(
            navController = navController
        )
    }
}
