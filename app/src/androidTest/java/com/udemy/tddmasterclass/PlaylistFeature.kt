package com.udemy.tddmasterclass

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udemy.tddmasterclass.data.idlingResource

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PlaylistFeature: BaseUITest() {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.udemy.tddmasterclass", appContext.packageName)
    }

    @Test
    fun displayScreenTitle() {
        composeTestRule.onNodeWithText("Playlists").assertIsDisplayed()
    }

    // TODO: do test wether lazycolumn / recyclerview is show or not
    // TODO: do test for recyclerView item if displayed and showing correct item
    @Test
    fun displayPlaylistLazyColumn() {
        composeTestRule.apply {
            onNodeWithTag("playlist-rv").assertIsDisplayed()
        }
    }

    @Test
    fun displayLoaderWileFetchingThePlaylist() {
//        IdlingRegistry.getInstance().unregister(idlingResource)
//        composeTestRule.onNodeWithTag("loader").assertIsDisplayed()
        // TODO: not implemented
    }

    @Test
    fun hidesLoader() {
        composeTestRule.onNodeWithTag("loader").assertDoesNotExist()
    }

    @Test
    fun navigateToDetailScreen() {
        // TODO: not implemented
    }
}