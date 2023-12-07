package com.pouyaa.takehomegithubapi.feature.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.model.User

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onRepoClicked: (Repo) -> Unit
) {
    HomeScreen(
        modifier = modifier,
        userUiState = viewModel.userUiState,
        reposUiState = viewModel.reposUiState,
        onRepoClicked = onRepoClicked,
        onSearchClicked = viewModel::onSearchCLicked
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    userUiState: HomeViewModel.UserUiState,
    reposUiState: HomeViewModel.ReposUiState,
    onRepoClicked: (Repo) -> Unit,
    onSearchClicked: (String) -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchToolbar(
            modifier = Modifier.padding(vertical = 8.dp),
            onSearchClicked = onSearchClicked
        )
        UserInfoView(modifier = Modifier.padding(vertical = 8.dp), uiState = userUiState)
        ReposListView(uiState = reposUiState, onRepoClicked = onRepoClicked)
    }
}

@Composable
private fun SearchToolbar(modifier: Modifier = Modifier, onSearchClicked: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.weight(weight = 1f, fill = false),
            value = query,
            onValueChange = { query = it },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                onSearchClicked(query)
            }),
            label = { Text(text = stringResource(id = R.string.search_hint)) })

        Button(
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            onClick = {
                focusManager.clearFocus()
                onSearchClicked(query)
            }, enabled = query.isNotBlank()
        ) {
            Text(text = stringResource(id = R.string.search_all_caps))
        }
    }
}

@Composable
fun UserInfoView(modifier: Modifier = Modifier, uiState: HomeViewModel.UserUiState) {
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
            is HomeViewModel.UserUiState.Success -> UserInfoView(modifier, state.user)
            HomeViewModel.UserUiState.Loading -> CircularProgressIndicator()
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
private fun UserInfoView(modifier: Modifier, user: User) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            modifier = Modifier.size(128.dp),
            model = user.avatarUrl,
            contentDescription = stringResource(id = R.string.user_avatar)
        )
        Text(
            text = user.name.takeIf(String::isNotBlank) ?: user.userId,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ReposListView(
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