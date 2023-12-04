package com.pouyaa.takehomegithubapi.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUser(
    @SerialName(value = "login") val userId: String?,
    @SerialName(value = "name") val name: String?,
    @SerialName(value = "avatar_url") val avatarUrl: String?
)
