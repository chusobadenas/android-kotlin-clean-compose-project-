package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.Toolbar

@Preview
@Composable
fun MainScreen(
    onNavigateToUsersList: (() -> Unit)? = null
) {
    Toolbar()
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .wrapContentHeight()
        ) {
            Image(
                contentDescription = stringResource(R.string.string_content_description),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                painter = painterResource(R.drawable.logo)
            )
            Button(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                onClick = {
                    onNavigateToUsersList?.invoke()
                }
            ) {
                Text(
                    text = stringResource(R.string.btn_text_load_data)
                )
            }
        }
    }
}
