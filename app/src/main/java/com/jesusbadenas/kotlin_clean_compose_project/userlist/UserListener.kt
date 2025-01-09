package com.jesusbadenas.kotlin_clean_compose_project.userlist

import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User

interface UserListener {
    fun onUserItemClicked(user: User)
}
