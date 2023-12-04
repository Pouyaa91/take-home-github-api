package com.pouyaa.takehomegithubapi.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRepo(
    @SerialName(value = "id") val id: Int?,
    @SerialName(value = "name") val name: String?,
    @SerialName(value = "description") val description: String?,
    @SerialName(value = "updated_at") val updatedAt: String?,
    @SerialName(value = "stargazers_count") val starsCount: Int?,
    @SerialName(value = "forks_count") val forksCount: Int?
)