package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen.MainScreen
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen.UserDetailScreen
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen.UsersListScreen

@Composable
fun AppNavHost(
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
