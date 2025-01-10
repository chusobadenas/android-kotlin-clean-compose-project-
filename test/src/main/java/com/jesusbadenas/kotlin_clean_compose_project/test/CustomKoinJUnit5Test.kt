package com.jesusbadenas.kotlin_clean_compose_project.test

import androidx.test.core.app.ApplicationProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class CustomKoinJUnit5Test(
    private vararg val modules: Module = emptyArray()
) : KoinTest {

    protected lateinit var app: KoinTestApp

    @BeforeEach
    fun setUp() {
        app = ApplicationProvider.getApplicationContext() as KoinTestApp
        app.loadModules(modules)
    }

    @AfterEach
    fun tearDown() {
        app.unloadModules(modules)
        stopKoin()
    }
}
