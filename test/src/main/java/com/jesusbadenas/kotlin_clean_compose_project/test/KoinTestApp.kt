package com.jesusbadenas.kotlin_clean_compose_project.test

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

class KoinTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinTestApp)
            modules(emptyList())
        }
    }

    fun loadModules(modules: Array<out Module>) {
        loadKoinModules(modules.toList())
    }

    fun unloadModules(modules: Array<out Module>) {
        unloadKoinModules(modules.toList())
    }
}
