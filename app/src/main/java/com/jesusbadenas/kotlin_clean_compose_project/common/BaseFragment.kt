package com.jesusbadenas.kotlin_clean_compose_project.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jesusbadenas.kotlin_clean_compose_project.navigation.Navigator
import org.koin.android.compat.ViewModelCompat.getViewModel
import org.koin.android.ext.android.inject
import kotlin.reflect.KClass

abstract class BaseFragment<B: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes private val layoutId: Int,
    private val viewModelClass: KClass<VM>
) : Fragment() {

    protected lateinit var binding: B
        private set
    protected lateinit var viewModel: VM
        private set

    protected val navigator: Navigator by inject()

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewModel = getViewModel(owner = this, clazz = viewModelClass.java)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            lifecycle.addObserver(viewModel)
        }

        arguments?.let { bundle ->
            if (!bundle.isEmpty) {
                getFragmentArgs(bundle)
            }
        }

        setUpDataBinding(binding)
        observeViewModel(viewModel)

        return binding.root
    }

    @CallSuper
    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    protected open fun getFragmentArgs(args: Bundle) {}

    protected abstract fun setUpDataBinding(binding: B)

    protected abstract fun observeViewModel(viewModel: VM)
}
