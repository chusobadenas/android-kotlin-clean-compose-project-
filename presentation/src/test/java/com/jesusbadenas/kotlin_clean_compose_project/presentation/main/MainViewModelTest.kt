package com.jesusbadenas.kotlin_clean_compose_project.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.test.extension.getOrAwaitValue
import io.mockk.MockKAnnotations
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
