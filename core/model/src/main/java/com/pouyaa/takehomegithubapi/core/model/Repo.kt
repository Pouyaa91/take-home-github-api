package com.pouyaa.takehomegithubapi.core.model

data class Repo(
    val id: Int,
    val name: String,
    val description: String,
    val updatedAt: String,
    val starsCount: Int,
    val forksCount: Int
)
