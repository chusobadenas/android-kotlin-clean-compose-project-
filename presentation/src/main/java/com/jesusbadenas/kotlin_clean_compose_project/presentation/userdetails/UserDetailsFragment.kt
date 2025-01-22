package com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseFragment
import com.jesusbadenas.kotlin_clean_compose_project.presentation.databinding.FragmentUserDetailsBinding
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIError
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIState
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.showError
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    processUIState(state, viewModel)
                }
            }
        }
    }

    private fun processUIState(state: UIState<User>, viewModel: UserDetailsViewModel) {
        with(viewModel) {
            when (state) {
                is UIState.Error -> {
                    showLoading(false)
                    showError(UIError(throwable = state.exception) {
                        loadUser(userId = navArgs.userId)
                    })
                }
                is UIState.Loading -> {
                    showLoading(true)
                }
                else -> {
                    showLoading(false)
                }
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
