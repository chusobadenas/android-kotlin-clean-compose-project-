package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.navigation.Route
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.theme.appTypography

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

@Composable
private fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.Main
    ) {
        composable<Route.Main> {
            MainScreen(
                onNavigateToUsersList = {
                    navController.navigate(route = Route.UsersList)
                }
            )
        }
        composable<Route.UsersList> {
            UsersListScreen(
                onNavigateToUserDetail = { userId ->
                    navController.navigate(route = Route.UserDetail(id = userId))
                }
            )
        }
        composable<Route.UserDetail> { backStackEntry ->
            val user = backStackEntry.toRoute<User>()
            UserDetailScreen(user = user)
        }
    }
}

@Composable
fun MainScreen(
    onNavigateToUsersList: () -> Unit
) {
    Toolbar()
}

@Composable
fun UsersListScreen(
    onNavigateToUserDetail: (userId: Int) -> Unit
) {
    // TODO
}

@Composable
fun UserDetailScreen(
    user: User
) {
    // TODO
}

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
