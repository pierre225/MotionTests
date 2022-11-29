package com.pierre.motiontest

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import com.pierre.ui.R
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import org.junit.Before


/**
 * UI tests on the app, ideally we should test each fragments and some user flows through the app
 *
 * Unfortunatly, I don't have enough time to do it now, and to be honest I would need to learn
 * a bit more on Espresso first ! ;)
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppUiTests : TestCase() {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun baseViewsAreDisplayed() {
        onView(withId(R.id.baseContainer)).check(matches(isDisplayed()))
        onView(withId(R.id.baseRoot)).check(matches(isDisplayed()))
    }
}
