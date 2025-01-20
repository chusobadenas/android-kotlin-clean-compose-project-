package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.theme.appTypography

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Toolbar() {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(
                        style = appTypography.titleLarge,
                        text = context.getString(R.string.app_name)
                    )
                }
            )
        }
    ) { innerPadding ->
        Spacer(modifier = Modifier.padding(innerPadding))
    }
}
