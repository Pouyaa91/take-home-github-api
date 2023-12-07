package com.pouyaa.takehomegithubapi.core.network.service

import com.pouyaa.takehomegithubapi.core.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    @GET("users/{userId}")
    suspend fun fetchUser(@Path("userId") userId: String): Response<NetworkUser>
}