package com.jesusbadenas.kotlin_clean_compose_project.main

import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.common.BaseFragment
import com.jesusbadenas.kotlin_clean_compose_project.common.LiveEventObserver
import com.jesusbadenas.kotlin_clean_compose_project.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    layoutId = R.layout.fragment_main,
    viewModelClass = MainViewModel::class
) {

    override fun setUpDataBinding(binding: FragmentMainBinding) {
        binding.viewModel = viewModel
    }

    override fun observeViewModel(viewModel: MainViewModel) {
        viewModel.loadAction.observe(viewLifecycleOwner, LiveEventObserver { load ->
            if (load) {
                navigateToUserList()
            }
        })
    }

    private fun navigateToUserList() {
        navigator.navigateToUserList(this)
    }
}
