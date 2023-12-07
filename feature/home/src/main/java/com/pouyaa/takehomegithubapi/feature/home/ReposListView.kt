package com.pouyaa.takehomegithubapi.feature.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pouyaa.takehomegithubapi.core.model.Repo

@Composable
internal fun ReposListView(
    modifier: Modifier = Modifier,
    uiState: HomeViewModel.ReposUiState,
    onRepoClicked: (Repo) -> Unit
) {
    AnimatedContent(
        targetState = uiState,
        label = "repos",
        transitionSpec = {
            fadeIn(tween(durationMillis = 300, delayMillis = 300)) + slideInVertically(
                animationSpec = tween(durationMillis = 600),
                initialOffsetY = { fullHeight -> fullHeight }
            ) togetherWith fadeOut(tween(delayMillis = 0))
        }
    ) { state ->
        when (state) {
            is HomeViewModel.ReposUiState.Error -> Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = state.message.orEmpty(),
                fontWeight = FontWeight.Bold
            )

            HomeViewModel.ReposUiState.Waiting -> {}
            is HomeViewModel.ReposUiState.Success -> ReposList(modifier, state.repos, onRepoClicked)
            HomeViewModel.ReposUiState.Empty -> Text(text = stringResource(id = R.string.empty_repo))
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ReposList(
    modifier: Modifier,
    repos: List<Repo>,
    onRepoClicked: (Repo) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(items = repos) { repo ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, top = 8.dp),
                onClick = { onRepoClicked(repo) },
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp
                    ),
                    text = repo.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    text = repo.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}