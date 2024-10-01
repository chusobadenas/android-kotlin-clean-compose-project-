package com.jesusbadenas.kotlin_clean_compose_project

import android.app.Application
import com.jesusbadenas.kotlin_clean_compose_project.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
