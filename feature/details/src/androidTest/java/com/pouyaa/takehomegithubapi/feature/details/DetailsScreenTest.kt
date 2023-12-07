package com.pouyaa.takehomegithubapi.feature.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun checkDetailsScreenShowsInfo() {
        composeTestRule.setContent {
            DetailsScreen(
                name = "repo name",
                description = "repo description",
                updatedAt = "2023",
                starsCount = 1,
                forksCount = 2,
                totalForks = 3
            )
        }
        composeTestRule.onNodeWithText("repo name").assertExists()
        composeTestRule.onNodeWithText("repo description").assertExists()
        composeTestRule.onNodeWithText("2023").assertExists()
        composeTestRule.onNodeWithText("1").assertExists()
        composeTestRule.onNodeWithText("2").assertExists()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.total_forks, 3)
        ).assertExists()
    }
}