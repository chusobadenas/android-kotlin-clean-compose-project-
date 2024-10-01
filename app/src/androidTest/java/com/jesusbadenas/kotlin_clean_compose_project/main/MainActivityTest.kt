package com.jesusbadenas.kotlin_clean_compose_project.main

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testMainFragmentIsOpenSuccess() {
        // Create activity
        ActivityScenario.launch(MainActivity::class.java).apply {
            moveToState(Lifecycle.State.RESUMED)
        }

        // Verify fragment is opened
        onView(withId(R.id.btn_LoadData)).check(matches((isDisplayed())))
    }
}
