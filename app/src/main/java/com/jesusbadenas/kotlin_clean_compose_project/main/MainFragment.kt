package com.jesusbadenas.kotlin_clean_compose_project.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.common.LiveEventObserver
import com.jesusbadenas.kotlin_clean_compose_project.databinding.FragmentMainBinding
import com.jesusbadenas.kotlin_clean_compose_project.navigation.Navigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val navigator: Navigator by inject()
    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this

        // View model
        binding.viewModel = viewModel
        subscribe()

        return binding.root
    }

    private fun subscribe() {
        viewModel.loadAction.observe(viewLifecycleOwner, LiveEventObserver { load ->
            if (load) {
                navigateToUserList()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showLoading(false)
    }

    private fun navigateToUserList() {
        navigator.navigateToUserList(this)
    }
}
