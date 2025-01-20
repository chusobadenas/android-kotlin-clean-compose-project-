package com.jesusbadenas.kotlin_clean_compose_project.presentation.userlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseFragment
import com.jesusbadenas.kotlin_clean_compose_project.presentation.databinding.FragmentUserListBinding
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIError
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIState
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.showError
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Fragment that shows a list of Users.
 */
class UserListFragment : BaseFragment<FragmentUserListBinding, UserListViewModel>(
    layoutId = R.layout.fragment_user_list,
    viewModelClass = UserListViewModel::class
), UserListener {

    private val usersAdapter: UserAdapter by inject {
        parametersOf(this@UserListFragment)
    }

    override fun setUpDataBinding(binding: FragmentUserListBinding) {
        binding.viewModel = viewModel
        setupRecyclerView(binding)
    }

    private fun setupRecyclerView(binding: FragmentUserListBinding) {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }
        binding.swipeContainer.apply {
            setColorSchemeResources(R.color.primary_light)
            setOnRefreshListener {
                viewModel.loadUserList()
            }
        }
    }

    override fun observeViewModel(viewModel: UserListViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    processUIState(state, viewModel)
                }
            }
        }
    }

    private fun processUIState(state: UIState<List<User>>, viewModel: UserListViewModel) {
        with(viewModel) {
            when (state) {
                is UIState.Empty -> {
                    showLoading(false)
                    showError(UIError(messageTextId = R.string.error_message_empty_list) {
                        loadUserList()
                    })
                }
                is UIState.Error -> {
                    showLoading(false)
                    showError(UIError(throwable = state.exception) {
                        loadUserList()
                    })
                }
                is UIState.Loading -> {
                    showLoading(true)
                }
                is UIState.Success -> {
                    showLoading(false)
                    submitList(users = state.data)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadUserList()
    }

    override fun onDestroyView() {
        binding.rvUsers.adapter = null
        super.onDestroyView()
    }

    private fun submitList(users: List<User>?) {
        if (binding.swipeContainer.isRefreshing) {
            binding.swipeContainer.isRefreshing = false
        }
        usersAdapter.submitList(users)
    }

    override fun onUserItemClicked(user: User) {
        navigator.navigateToUserDetails(fragment = this, userId = user.id)
    }
}
