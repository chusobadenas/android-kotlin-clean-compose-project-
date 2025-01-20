package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoadingView() {
    CircularProgressIndicator(
        modifier = Modifier.wrapContentSize()
    )
}
