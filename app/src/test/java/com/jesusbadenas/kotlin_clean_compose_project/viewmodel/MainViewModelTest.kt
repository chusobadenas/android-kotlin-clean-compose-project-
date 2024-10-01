package com.jesusbadenas.kotlin_clean_compose_project.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var observer: Observer<Void>

    private lateinit var mainVM: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainVM = MainViewModel()
    }

    @Test
    fun testOnLoadButtonClick() {
        every { observer.onChanged(any()) } just Runs

        mainVM.loadAction.observeForever(observer)
        mainVM.onLoadButtonClick()

        verify { observer.onChanged(any()) }
    }
}
