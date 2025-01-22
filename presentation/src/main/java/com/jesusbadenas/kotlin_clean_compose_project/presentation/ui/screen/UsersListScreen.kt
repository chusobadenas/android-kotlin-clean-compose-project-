package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIState
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.ItemUser
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.LoadingView
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.Toolbar
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist.UserListViewModel
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun UsersListScreen(
    onNavigateToUserDetail: ((userId: Int) -> Unit)? = null
) {
    val viewModel: UserListViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(top = 64.dp)
        ) {
            UsersListRecyclerView(
                uiState = uiState,
                users = (uiState as? UIState.Success)?.data.orEmpty(),
                viewModel = viewModel,
                onNavigateToUserDetail = onNavigateToUserDetail
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingView(visible = uiState is UIState.Loading)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UsersListRecyclerView(
    uiState: UIState<List<User>>,
    users: List<User>,
    viewModel: UserListViewModel,
    onNavigateToUserDetail: ((userId: Int) -> Unit)? = null
) {
    PullToRefreshBox(
        contentAlignment = Alignment.Center,
        isRefreshing = uiState is UIState.Loading,
        modifier = Modifier.fillMaxSize(),
        onRefresh = {
            viewModel.loadUserList()
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(users) { user ->
                ItemUser(
                    user = user,
                    onNavigateToUserDetail = onNavigateToUserDetail
                )
            }
        }
    }
}
