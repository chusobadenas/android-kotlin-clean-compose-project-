package com.jesusbadenas.kotlin_clean_compose_project.userlist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.common.BaseFragment
import com.jesusbadenas.kotlin_clean_compose_project.common.LiveEventObserver
import com.jesusbadenas.kotlin_clean_compose_project.common.showError
import com.jesusbadenas.kotlin_clean_compose_project.databinding.FragmentUserListBinding
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
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

    override fun observeViewModel(viewModel: UserListViewModel) {
        with(viewModel) {
            retryAction.observe(viewLifecycleOwner, LiveEventObserver { load ->
                if (load) {
                    loadUserList()
                }
            })
            userList.observe(viewLifecycleOwner) { users ->
                loadUserList(users)
            }
            uiError.observe(viewLifecycleOwner) { uiError ->
                showError(uiError)
            }
        }
    }

    private fun setupRecyclerView(binding: FragmentUserListBinding) {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }
        binding.swipeContainer.apply {
            setColorSchemeResources(R.color.primary)
            setOnRefreshListener {
                viewModel.loadUserList()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            showLoading(true)
            loadUserList()
        }
    }

    override fun onDestroyView() {
        binding.rvUsers.adapter = null
        super.onDestroyView()
    }

    private fun loadUserList(users: List<User>?) {
        if (binding.swipeContainer.isRefreshing) {
            binding.swipeContainer.isRefreshing = false
        }
        usersAdapter.submitList(users)
    }

    override fun onUserItemClicked(user: User) {
        navigator.navigateToUserDetails(fragment = this, userId = user.id)
    }
}
