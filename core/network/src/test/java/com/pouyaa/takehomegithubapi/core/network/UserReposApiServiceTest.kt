package com.pouyaa.takehomegithubapi.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pouyaa.takehomegithubapi.core.network.model.NetworkMessage
import com.pouyaa.takehomegithubapi.core.network.service.UserReposApiService
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.io.File

class UserReposApiServiceTest {

    private lateinit var apiService: UserReposApiService
    private lateinit var mockWebServer: MockWebServer

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Before
    fun setup() {
        mockWebServer = MockWebServer().apply(MockWebServer::start)
        apiService = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(UserReposApiService::class.java)
    }

    @Test
    fun checkUserReposConvertsCorrectly() = runTest {
        val mockResponse = File("src/test/resources/user_repos.json").readText()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse))
        val repos = apiService.fetchUserRepos("").body()
        assertEquals(repos?.size, 1)
        assertEquals(repos?.firstOrNull()?.name, "name")
        assertEquals(repos?.firstOrNull()?.id, 1)
        assertEquals(repos?.firstOrNull()?.description, "description")
        assertEquals(repos?.firstOrNull()?.forksCount, 1)
        assertEquals(repos?.firstOrNull()?.starsCount, 20)
        assertEquals(repos?.firstOrNull()?.updatedAt, "2023-10-24T03:39:44Z")
    }

    @Test
    fun checkErrorConvertsCorrectly() = runTest {
        val mockResponse = File("src/test/resources/error_response.json").readText()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(404))
        val response = apiService.fetchUserRepos("")
        val networkMessage =
            json.decodeFromString<NetworkMessage>(response.errorBody()?.string().orEmpty())
        assertEquals(networkMessage.message, "Not Found")
        assertEquals(response.code(), 404)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}