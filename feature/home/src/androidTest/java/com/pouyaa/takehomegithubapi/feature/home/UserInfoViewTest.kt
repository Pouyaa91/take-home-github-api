package com.pouyaa.takehomegithubapi.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.pouyaa.takehomegithubapi.core.model.User
import org.junit.Rule
import org.junit.Test

class UserInfoViewTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun checkUiOnLoadingState() {
        composeTestRule.setContent {
            UserInfoView(
                uiState = HomeViewModel.UserUiState.Loading
            )
        }
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(R.string.loading)
        ).assertExists()
    }

    @Test
    fun checkUiOnErrorState() {
        composeTestRule.setContent {
            UserInfoView(
                uiState = HomeViewModel.UserUiState.Error(message = "message")
            )
        }
        composeTestRule.onNodeWithText("message").assertExists()
    }

    @Test
    fun checkUiOnSuccessState() {
        val user = User(userId = "userId", name = "user name", avatarUrl = "url")
        composeTestRule.setContent {
            UserInfoView(
                uiState = HomeViewModel.UserUiState.Success(user = user)
            )
        }
        composeTestRule.onNodeWithText(user.name).assertExists()
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(R.string.user_avatar)
        )
    }
}