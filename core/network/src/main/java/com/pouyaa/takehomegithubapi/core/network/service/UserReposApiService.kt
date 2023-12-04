package com.pouyaa.takehomegithubapi.core.network.service

import com.pouyaa.takehomegithubapi.core.network.model.NetworkRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface UserReposApiService {

    @GET("users/{userId}/repos")
    suspend fun fetchUserRepos(@Path("userId") userId: String): List<NetworkRepo>
}