package com.pouyaa.takehomegithubapi.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pouyaa.takehomegithubapi.core.model.Repo

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