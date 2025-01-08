package com.jesusbadenas.kotlin_clean_compose_project.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jesusbadenas.kotlin_clean_compose_project.test.extension.getOrAwaitValue
import io.mockk.MockKAnnotations
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel()
    }

    @Test
    fun `test on load button click success`() {
        viewModel.onLoadButtonClick()

        val result = viewModel.loadAction.getOrAwaitValue()

        Assert.assertTrue(result.peekContent())
    }
}
