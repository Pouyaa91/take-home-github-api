package com.pouyaa.takehomegithubapi.feature.details

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
internal fun RepoInfoChip(label: String, imageVector: ImageVector) {
    AssistChip(
        onClick = { },
        label = { Text(text = label) },
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = label) },
        enabled = false,
        colors = AssistChipDefaults.assistChipColors(
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            disabledLeadingIconContentColor = MaterialTheme.colorScheme.primary
        ),
        border = AssistChipDefaults.assistChipBorder(disabledBorderColor = MaterialTheme.colorScheme.outline)
    )
}