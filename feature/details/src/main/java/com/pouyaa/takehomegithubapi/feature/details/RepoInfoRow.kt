package com.pouyaa.takehomegithubapi.feature.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun RepoInfoRow(
    updatedAt: String,
    starsCount: Int,
    forksCount: Int,
    modifier: Modifier = Modifier
) {
    FlowRow(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

        RepoInfoChip(label = starsCount.toString(), imageVector = Icons.Outlined.Star)

        RepoInfoChip(
            label = forksCount.toString(),
            imageVector = Icons.Outlined.AccountTree
        )

        RepoInfoChip(label = updatedAt, imageVector = Icons.Outlined.AccessTime)
    }
}
