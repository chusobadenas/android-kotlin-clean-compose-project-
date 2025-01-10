package com.jesusbadenas.kotlin_clean_compose_project.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jesusbadenas.kotlin_clean_compose_project.presentation.main.MainFragmentDirections
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist.UserListFragmentDirections

/**
 * Class used to navigate through the application.
 */
class Navigator {

    fun navigateToUserList(fragment: Fragment) {
        val directions = MainFragmentDirections.navigateToUserListFragment()
        fragment.findNavController().navigate(directions)
    }

    fun navigateToUserDetails(fragment: Fragment, userId: Int) {
        val directions = UserListFragmentDirections.navigateToUserDetailsFragment(userId)
        fragment.findNavController().navigate(directions)
    }
}
