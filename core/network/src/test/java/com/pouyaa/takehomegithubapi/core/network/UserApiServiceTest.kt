package com.pouyaa.takehomegithubapi.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pouyaa.takehomegithubapi.core.network.service.UserApiService
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

class UserApiServiceTest {

    private lateinit var apiService: UserApiService
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
            .create(UserApiService::class.java)
    }

    @Test
    fun checkUserConvertsCorrectly() = runTest {
        val mockResponse = File("src/test/resources/user.json").readText()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse))
        val user = apiService.fetchUser("")

        assertEquals(user.name, "user name")
        assertEquals(user.avatarUrl, "avatar url")
        assertEquals(user.userId, "user id")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}