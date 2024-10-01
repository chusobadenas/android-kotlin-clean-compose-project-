package com.jesusbadenas.kotlin_clean_compose_project.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.common.showError
import com.jesusbadenas.kotlin_clean_compose_project.databinding.FragmentUserDetailsBinding
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.viewmodel.UserDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment that shows details of a certain User.
 */
class UserDetailsFragment : Fragment() {

    private val navArgs: UserDetailsFragmentArgs by navArgs()
    private val viewModel: UserDetailsViewModel by viewModel {
        parametersOf(navArgs.userId)
    }

    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data binding
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        binding.lifecycleOwner = this

        // View model
        binding.viewModel = viewModel
        binding.viewProgress.viewModel = viewModel
        binding.viewRetry.viewModel = viewModel
        subscribe()

        return binding.root
    }

    private fun subscribe() {
        viewModel.retryAction.observe(viewLifecycleOwner) {
            viewModel.loadUser()
        }
        viewModel.user.observe(viewLifecycleOwner) { user ->
            loadUserDetails(user)
        }
        viewModel.uiError.observe(viewLifecycleOwner) { uiError ->
            showError(uiError)
        }
    }

    private fun loadUserDetails(user: User?) {
        viewModel.showLoading(View.GONE)
        viewModel.showRetry(viewModel.retryVisibility.value!!)
        binding.user = user
    }
}
