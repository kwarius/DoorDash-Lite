package com.ruslan.doordashlite

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ruslan.doordashlite.Util.getOkHttpClient
import com.ruslan.doordashlite.Util.readStringFromFile
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    lateinit var mServer: MockWebServer

    @get:Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        mServer = MockWebServer()
        mServer.start(8080)

        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                getOkHttpClient()
            )
        )
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mServer.shutdown()
    }

    @Test
    fun restaurantsListAndScroll() {
        mServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(readStringFromFile("restaurant_expresso.json"))
            }
        }

        activityRule.launchActivity(null)

        SystemClock.sleep(2000)

        onView(withId(R.id.mainProgress))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.restaurantRecyclerView))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.errorText))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.restaurantRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.restaurantRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
    }

    @Test
    fun restaurantClick() {
        mServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(readStringFromFile("restaurant_expresso.json"))
            }
        }

        activityRule.launchActivity(null)
        SystemClock.sleep(2000)
        onView(withId(R.id.restaurantRecyclerView))
            .waitUntilVisible(2000)
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    3,
                    ViewActions.click()
                )
            )
    }

    @Test
    fun restaurantClickAndDetails() {
        mServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(readStringFromFile("restaurant_expresso.json"))
            }
        }

        activityRule.launchActivity(null)
        SystemClock.sleep(2000)
        onView(withId(R.id.restaurantRecyclerView))

            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2,
                    ViewActions.click()
                )
            )

        SystemClock.sleep(2000)
        onView(withId(R.id.restaurantName))
            .check(matches(withText("L&L Hawaiian BBQ (El Camino Real)")))
        onView(withId(R.id.restaurantDescription))
            .check(matches(withText("L&L Hawaiian Barbecue")))
        onView(withId(R.id.restaurantStatus))
            .check(matches(withText("26 - 36 mins")))
    }

    @Test
    fun testFailedResponse() {
        mServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }

        activityRule.launchActivity(null)

        onView(withId(R.id.mainProgress))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.restaurantRecyclerView))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.errorText))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.errorText))
            .check(matches(withText("No restaurants found for this location")))
    }

    private fun ViewInteraction.waitUntilVisible(timeout: Long): ViewInteraction {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + timeout

        do {
            try {
                check(matches(isDisplayed()))
                return this
            } catch (e: NoMatchingViewException) {
                Thread.sleep(2000)
            }
        } while (System.currentTimeMillis() < endTime)

        throw TimeoutException()
    }

}