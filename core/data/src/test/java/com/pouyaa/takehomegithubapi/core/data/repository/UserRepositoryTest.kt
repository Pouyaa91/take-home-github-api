package com.pouyaa.takehomegithubapi.core.data.repository

import com.pouyaa.takehomegithubapi.core.data.mapper.NetworkUserToUserMapper
import com.pouyaa.takehomegithubapi.core.data.repository.user.UserRepositoryImpl
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.model.User
import com.pouyaa.takehomegithubapi.core.network.model.NetworkUser
import com.pouyaa.takehomegithubapi.core.network.service.UserApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    @MockK
    lateinit var mapper: NetworkUserToUserMapper

    @MockK
    lateinit var apiService: UserApiService

    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = UserRepositoryImpl(apiService = apiService, mapper = mapper)
    }

    @Test
    fun checkRepositoryReturnsCorrectlyOnSuccess() = runTest {
        val mappedResult = User(userId = "id", name = "name", avatarUrl = "image url")

        coEvery { apiService.fetchUser(any()) } returns
                NetworkUser(userId = null, name = null, avatarUrl = null)

        coEvery { mapper.map(any()) } returns mappedResult

        repository.fetch("").collectLatest { result ->
            val user = (result as? Result.Success)?.data
            assertEquals(user, mappedResult)
        }

        coVerifySequence {
            apiService.fetchUser(any())
            mapper.map(any())
        }
    }

    @Test
    fun checkRepositoryReturnsCorrectlyOnException() = runTest {
        coEvery { apiService.fetchUser(any()) } throws Exception("test exception")

        repository.fetch("").collectLatest { result ->
            val message = (result as? Result.Error)?.throwable?.message
            assertEquals("test exception", message)
        }

        coVerifySequence {
            apiService.fetchUser(any())
        }
    }
}