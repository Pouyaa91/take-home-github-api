package com.pouyaa.takehomegithubapi.feature.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pouyaa.takehomegithubapi.core.model.User

@Composable
internal fun UserInfoView(modifier: Modifier = Modifier, uiState: HomeViewModel.UserUiState) {
    val loading = stringResource(id = R.string.loading)
    AnimatedContent(
        targetState = uiState,
        label = "info",
        transitionSpec = {
            fadeIn(tween(durationMillis = 300, delayMillis = 300)) +
                    slideInVertically(
                        animationSpec = tween(durationMillis = 600),
                        initialOffsetY = { fullHeight -> fullHeight }
                    ) togetherWith fadeOut(tween(0))
        }
    ) { state ->
        when (state) {
            is HomeViewModel.UserUiState.Success -> UserInfo(modifier, state.user)

            HomeViewModel.UserUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.semantics { contentDescription = loading })

            is HomeViewModel.UserUiState.Error -> Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = state.message.orEmpty(),
                fontWeight = FontWeight.Bold
            )

            HomeViewModel.UserUiState.Waiting -> {}
        }
    }
}

@Composable
private fun UserInfo(modifier: Modifier = Modifier, user: User) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            modifier = Modifier.size(128.dp),
            model = user.avatarUrl,
            contentDescription = stringResource(id = R.string.user_avatar)
        )
        Text(
            text = user.name.takeIf(String::isNotBlank) ?: user.userId,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserInfoPreview() {
    UserInfo(user = User(userId = "0", name = "Name", avatarUrl = ""))
}