package com.jesusbadenas.kotlin_clean_compose_project.test

import androidx.annotation.CallSuper
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.koin.core.module.Module
import org.koin.test.AutoCloseKoinTest

open class CustomKoinJUnit4Test(
    private vararg val modules: Module = emptyArray()
) : AutoCloseKoinTest() {

    protected lateinit var app: KoinTestApp

    @Before
    @CallSuper
    open fun setUp() {
        app = ApplicationProvider.getApplicationContext() as KoinTestApp
        app.loadModules(modules)
    }

    @After
    @CallSuper
    open fun tearDown() {
        app.unloadModules(modules)
    }
}
