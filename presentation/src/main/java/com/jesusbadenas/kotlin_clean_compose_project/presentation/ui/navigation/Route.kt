package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data class UserDetail(val id: Int) : Route
    @Serializable
    data object UsersList : Route
    @Serializable
    data object Main : Route
}
