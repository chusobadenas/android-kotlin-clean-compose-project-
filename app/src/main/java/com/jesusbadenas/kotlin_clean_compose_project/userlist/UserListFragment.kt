package com.jesusbadenas.kotlin_clean_compose_project.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.common.showError
import com.jesusbadenas.kotlin_clean_compose_project.databinding.FragmentUserListBinding
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.navigation.Navigator
import com.jesusbadenas.kotlin_clean_compose_project.viewmodel.UserListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment that shows a list of Users.
 */
class UserListFragment : Fragment(), UserAdapter.OnItemClickListener {

    private val navigator: Navigator by inject()
    private val usersAdapter: UserAdapter by inject()
    private val viewModel: UserListViewModel by viewModel()

    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = this

        // View model
        binding.viewModel = viewModel
        binding.viewProgress.viewModel = viewModel
        binding.viewRetry.viewModel = viewModel
        subscribe()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvUsers.adapter = null
    }

    private fun setupRecyclerView() {
        usersAdapter.onItemClickListener = this
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }

        binding.swipeContainer.setColorSchemeResources(R.color.primary)
        binding.swipeContainer.setOnRefreshListener {
            viewModel.loadUserList()
        }
    }

    private fun subscribe() {
        viewModel.retryAction.observe(viewLifecycleOwner) {
            viewModel.loadUserList()
        }
        viewModel.userList.observe(viewLifecycleOwner) { users ->
            loadUserList(users)
        }
        viewModel.uiError.observe(viewLifecycleOwner) { uiError ->
            showError(uiError)
        }
    }

    private fun loadUserList(users: List<User>?) {
        viewModel.showLoading(View.GONE)
        viewModel.showRetry(viewModel.retryVisibility.value!!)
        if (binding.swipeContainer.isRefreshing) {
            binding.swipeContainer.isRefreshing = false
        }
        usersAdapter.submitList(users)
    }

    override fun onUserItemClicked(user: User) {
        navigator.navigateToUserDetails(this, user.userId)
    }
}
