package com.pouyaa.takehomegithubapi.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp

@Composable
fun DetailsRoute(
    name: String,
    description: String,
    updatedAt: String,
    starsCount: Int,
    forksCount: Int
) {
    DetailsScreen(
        name = name,
        description = description,
        updatedAt = updatedAt,
        starsCount = starsCount,
        forksCount = forksCount
    )
}

@Composable
fun DetailsScreen(
    name: String,
    description: String,
    updatedAt: String,
    starsCount: Int,
    forksCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        RepoInfoRow(updatedAt = updatedAt, starsCount = starsCount, forksCount = forksCount)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview(@PreviewParameter(LoremIpsum::class) sampleText: String) {
    DetailsRoute(
        sampleText,
        sampleText,
        "2023-12-06 21:18:23",
        11938,
        140283
    )
}