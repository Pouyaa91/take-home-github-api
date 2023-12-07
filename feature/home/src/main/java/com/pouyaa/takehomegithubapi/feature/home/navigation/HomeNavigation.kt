package com.pouyaa.takehomegithubapi.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.feature.home.HomeRoute

const val homeNavigationRoute = "home_route"
typealias TotalForks = Int

fun NavGraphBuilder.homeScreen(onRepoClicked: (Repo, TotalForks) -> Unit) {
    composable(route = homeNavigationRoute) {
        HomeRoute(onRepoClicked = onRepoClicked)
    }
}