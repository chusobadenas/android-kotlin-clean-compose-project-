package com.jesusbadenas.kotlin_clean_compose_project.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import com.jesusbadenas.kotlin_clean_compose_project.presentation.common.BaseActivity
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.App
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.theme.AppTheme

/**
 * Main application screen. This is the app entry point.
 */
class MainActivity : BaseActivity<MainViewModel>(
    viewModelClass = MainViewModel::class
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                App()
            }
        }
    }

    override fun observeViewModel(viewModel: MainViewModel) {}
}
