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
private const val USER_TOTAL_FORKS = "user_total_forks"
private const val DETAILS_ARGS =
    "/{$REPO_NAME}/{$REPO_DESCRIPTION}/{$REPO_UPDATED_AT}/{$REPO_STARS_COUNT}/{$REPO_FORKS_COUNT}/{$USER_TOTAL_FORKS}"

private const val DETAILS_NAVIGATION_ROUTE = DETAILS_ROUTE + DETAILS_ARGS

fun NavController.navigateToDetailsScreen(repo: Repo, totalForks: Int) {
    navigate(
        "$DETAILS_ROUTE/" +
                "${repo.name.takeIf(String::isNotBlank)?.replaceSlashes()}/" +
                "${repo.description.takeIf(String::isNotBlank)?.replaceSlashes()}/" +
                "${repo.updatedAt.takeIf(String::isNotBlank)?.replaceSlashes()}/" +
                "${repo.starsCount}/" +
                "${repo.forksCount}/" +
                "$totalForks"
    )
}

fun NavGraphBuilder.detailsScreen() {

    composable(
        route = DETAILS_NAVIGATION_ROUTE,
        arguments = listOf(
            navArgument(REPO_NAME) {
                nullable = true
                type = NavType.StringType
            },
            navArgument(REPO_DESCRIPTION) {
                nullable = true
                type = NavType.StringType
            },
            navArgument(REPO_UPDATED_AT) {
                nullable = true
                type = NavType.StringType
            },
            navArgument(REPO_STARS_COUNT) { type = NavType.IntType },
            navArgument(REPO_FORKS_COUNT) { type = NavType.IntType },
            navArgument(USER_TOTAL_FORKS) { type = NavType.IntType }
        )
    ) { backStackEntry ->
        DetailsRoute(
            name = backStackEntry.arguments?.getString(REPO_NAME)?.getOriginalString().orEmpty(),
            description = backStackEntry.arguments?.getString(REPO_DESCRIPTION)?.getOriginalString()
                .orEmpty(),
            updatedAt = backStackEntry.arguments?.getString(REPO_UPDATED_AT)?.getOriginalString()
                .orEmpty(),
            starsCount = backStackEntry.arguments?.getInt(REPO_STARS_COUNT) ?: 0,
            forksCount = backStackEntry.arguments?.getInt(REPO_FORKS_COUNT) ?: 0,
            totalForks = backStackEntry.arguments?.getInt(USER_TOTAL_FORKS) ?: 0
        )
    }
}

private const val TEMP_VALUE = "--*&^replace(*&--"

private fun String.replaceSlashes(): String = replace("/", TEMP_VALUE)

private fun String.getOriginalString(): String = replace(TEMP_VALUE, "/")