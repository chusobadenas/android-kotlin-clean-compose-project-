package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.LoadingView
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.Toolbar

@Preview
@Composable
fun UsersListScreen(
    onNavigateToUserDetail: ((userId: Int) -> Unit)? = null
) {
    Toolbar()
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 64.dp)
            .wrapContentHeight(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
        ) {
            // TODO: add refresh swipe layout
            UsersListRecyclerView(users = emptyList())
            LoadingView()
        }
    }
}

@Composable
fun UsersListRecyclerView(
    users: List<User>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(users) { user ->
            Text(text = user.name.orEmpty())
        }
    }
}
