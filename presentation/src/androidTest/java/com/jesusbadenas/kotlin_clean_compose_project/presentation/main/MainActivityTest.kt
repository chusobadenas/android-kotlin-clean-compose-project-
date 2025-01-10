package com.jesusbadenas.kotlin_clean_compose_project.presentation.main

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.data.di.dataModule
import com.jesusbadenas.kotlin_clean_compose_project.domain.di.domainModule
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.di.appModule
import com.jesusbadenas.kotlin_clean_compose_project.test.CustomKoinTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest: CustomKoinTest(dataModule, domainModule, appModule) {

    @Test
    fun testMainFragmentIsOpenSuccess() {
        // Create activity
        ActivityScenario.launch(MainActivity::class.java).apply {
            moveToState(Lifecycle.State.RESUMED)
        }

        // Verify fragment is opened
        onView(withId(R.id.button_load_data)).check(matches((isDisplayed())))
    }
}
