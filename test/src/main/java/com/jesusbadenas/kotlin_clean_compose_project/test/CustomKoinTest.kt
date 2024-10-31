package com.jesusbadenas.kotlin_clean_compose_project.test

import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.koin.core.module.Module
import org.koin.test.AutoCloseKoinTest

open class CustomKoinTest(
    private vararg val modules: Module = emptyArray()
) : AutoCloseKoinTest() {

    protected lateinit var app: KoinTestApp

    @Before
    fun init() {
        app = ApplicationProvider.getApplicationContext() as KoinTestApp
        app.loadModules(modules)
    }

    @After
    fun finish() {
        app.unloadModules(modules)
    }
}
