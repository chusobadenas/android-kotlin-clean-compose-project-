package com.jesusbadenas.kotlin_clean_compose_project.presentation.common

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import org.koin.android.compat.ViewModelCompat.getViewModel
import kotlin.reflect.KClass

abstract class BaseActivity<VM: BaseViewModel>(
    private val viewModelClass: KClass<VM>
) : FragmentActivity() {

    protected lateinit var viewModel: VM
        private set

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(owner = this, clazz = viewModelClass.java)

        intent.extras?.let { bundle ->
            if (!bundle.isEmpty) {
                getActivityExtras(bundle)
            }
        }

        observeViewModel(viewModel)
    }

    protected open fun getActivityExtras(extras: Bundle) {}

    protected abstract fun observeViewModel(viewModel: VM)
}
