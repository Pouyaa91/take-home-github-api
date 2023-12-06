package com.pouyaa.takehomegithubapi.feature.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.feature.details.DetailsRoute

private const val DETAILS_ROUTE = "details_route"
private const val REPO_NAME = "repo_name"
private const val REPO_DESCRIPTION = "repo_description"
private const val REPO_UPDATED_AT = "repo_updated_at"
private const val REPO_STARS_COUNT = "repo_stars_count"
private const val REPO_FORKS_COUNT = "repo_forks_count"
private const val DETAILS_ARGS =
    "/{$REPO_NAME}/{$REPO_DESCRIPTION}/{$REPO_UPDATED_AT}/{$REPO_STARS_COUNT}/{$REPO_FORKS_COUNT}"

private const val DETAILS_NAVIGATION_ROUTE = DETAILS_ROUTE + DETAILS_ARGS

fun NavController.navigateToDetailsScreen(repo: Repo) {
    navigate("$DETAILS_ROUTE/${repo.name}/${repo.description}/${repo.updatedAt}/${repo.starsCount}/${repo.forksCount}")
}

fun NavGraphBuilder.detailsScreen() {

    composable(
        route = DETAILS_NAVIGATION_ROUTE,
        arguments = listOf(
            navArgument(REPO_NAME) { type = NavType.StringType },
            navArgument(REPO_DESCRIPTION) { type = NavType.StringType },
            navArgument(REPO_UPDATED_AT) { type = NavType.StringType },
            navArgument(REPO_STARS_COUNT) { type = NavType.IntType },
            navArgument(REPO_FORKS_COUNT) { type = NavType.IntType }
        )
    ) { backStackEntry ->
        DetailsRoute(
            name = backStackEntry.arguments?.getString(REPO_NAME).orEmpty(),
            description = backStackEntry.arguments?.getString(REPO_DESCRIPTION).orEmpty(),
            updatedAt = backStackEntry.arguments?.getString(REPO_UPDATED_AT).orEmpty(),
            starsCount = backStackEntry.arguments?.getInt(REPO_STARS_COUNT) ?: 0,
            forksCount = backStackEntry.arguments?.getInt(REPO_FORKS_COUNT) ?: 0
        )
    }
}