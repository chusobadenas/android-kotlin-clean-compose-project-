package com.jesusbadenas.kotlin_clean_compose_project.presentation.main

import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseFragment
import com.jesusbadenas.kotlin_clean_compose_project.presentation.util.LiveEventObserver
import com.jesusbadenas.kotlin_clean_compose_project.presentation.databinding.FragmentMainBinding

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
