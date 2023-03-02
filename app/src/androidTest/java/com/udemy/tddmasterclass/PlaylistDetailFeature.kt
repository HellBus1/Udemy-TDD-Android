package com.udemy.tddmasterclass

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class PlaylistDetailFeature : BaseUITest() {
    @Test
    fun displayPlaylistNameAndDetails() {
        composeTestRule.onNodeWithTag("playlist-rv").assertIsDisplayed()

//        composeTestRule.apply {
//            // Scroll to first child
//            onNodeWithTag("playlist-rv")
//                .performScrollTo()
//                .performClick()
//        }

        composeTestRule.onNodeWithTag("name").assertIsDisplayed()
        composeTestRule.onNodeWithTag("description").assertIsNotDisplayed()
    }
}