package com.pouyaa.takehomegithubapi.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun TotalUserForks(totalForks: Int) {
    Row(
        modifier = Modifier.padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (totalForks > 5000) {
            Image(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                colorFilter = ColorFilter.tint(colorResource(id = R.color.gold))
            )
        }
        Text(
            text = stringResource(id = R.string.total_forks, totalForks),
            fontWeight = FontWeight.Bold,
            color = if (totalForks > 5000) colorResource(id = R.color.gold) else MaterialTheme.colorScheme.onSurface
        )
    }
}