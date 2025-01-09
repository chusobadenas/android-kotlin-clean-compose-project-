package com.jesusbadenas.kotlin_clean_compose_project.userdetails

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.common.BaseFragment
import com.jesusbadenas.kotlin_clean_compose_project.common.LiveEventObserver
import com.jesusbadenas.kotlin_clean_compose_project.common.showError
import com.jesusbadenas.kotlin_clean_compose_project.databinding.FragmentUserDetailsBinding
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User

/**
 * Fragment that shows details of a certain User.
 */
class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding, UserDetailsViewModel>(
    layoutId = R.layout.fragment_user_details,
    viewModelClass = UserDetailsViewModel::class
) {

    private val navArgs: UserDetailsFragmentArgs by navArgs()

    override fun setUpDataBinding(binding: FragmentUserDetailsBinding) {
        binding.viewModel = viewModel
    }

    override fun observeViewModel(viewModel: UserDetailsViewModel) {
        with(viewModel) {
            retryAction.observe(viewLifecycleOwner, LiveEventObserver { load ->
                if (load) {
                    loadUser(userId = navArgs.userId)
                }
            })
            uiError.observe(viewLifecycleOwner) { uiError ->
                showError(uiError)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            showLoading(true)
            loadUser(userId = navArgs.userId)
        }
    }
}
