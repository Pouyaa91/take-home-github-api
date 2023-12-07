package com.pouyaa.takehomegithubapi.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.pouyaa.takehomegithubapi.core.model.Repo
import org.junit.Rule
import org.junit.Test

class ReposListViewTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun checkUiOnEmptyState() {
        composeTestRule.setContent {
            ReposListView(
                uiState = HomeViewModel.ReposUiState.Empty,
                onRepoClicked = {}
            )
        }
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.empty_repo)
        ).assertExists()
    }

    @Test
    fun checkUiOnErrorState() {
        composeTestRule.setContent {
            ReposListView(
                uiState = HomeViewModel.ReposUiState.Error(message = "message"),
                onRepoClicked = {}
            )
        }
        composeTestRule.onNodeWithText("message").assertExists()
    }

    @Test
    fun checkUiOnSuccessState() {
        val repo = Repo(
            id = 0,
            name = "name",
            description = "description",
            updatedAt = "update",
            starsCount = 0,
            forksCount = 0
        )
        composeTestRule.setContent {
            ReposListView(
                uiState = HomeViewModel.ReposUiState.Success(repos = listOf(repo)),
                onRepoClicked = {}
            )
        }
        composeTestRule.onNodeWithText(repo.name).assertExists()
        composeTestRule.onNodeWithText(repo.description).assertExists()
    }
}