package com.jesusbadenas.kotlin_clean_compose_project.test.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.jesusbadenas.kotlin_clean_compose_project.test.KoinTestApp

class KoinTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, KoinTestApp::class.java.name, context)
}
