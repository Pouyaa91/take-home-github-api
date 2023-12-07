package com.pouyaa.takehomegithubapi.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkMessage(
    val message: String?
)