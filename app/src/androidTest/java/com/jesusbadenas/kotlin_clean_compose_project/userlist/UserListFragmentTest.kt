package com.jesusbadenas.kotlin_clean_compose_project.userlist

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesusbadenas.kotlin_clean_compose_project.R
import com.jesusbadenas.kotlin_clean_compose_project.main.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserListFragmentTest {

    @Test
    fun testNavigateToDetailFragmentSuccess() {
        // Create activity
        ActivityScenario.launch(MainActivity::class.java).apply {
            moveToState(Lifecycle.State.RESUMED)
        }

        // Verify fragment is opened
        onView(withId(R.id.btn_LoadData)).perform(click())
        onView(withId(R.id.rv_users))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.user_detail_view)).check(matches((isDisplayed())))
    }
}
