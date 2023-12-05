package com.pouyaa.takehomegithubapi.core.data.repository

import com.pouyaa.takehomegithubapi.core.data.mapper.NetworkRepoToRepoListMapper
import com.pouyaa.takehomegithubapi.core.data.repository.userrepo.UserReposListRepositoryImpl
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.network.service.UserReposApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserReposListRepositoryTest {

    @MockK
    lateinit var mapper: NetworkRepoToRepoListMapper

    @MockK
    lateinit var apiService: UserReposApiService

    private lateinit var repository: UserReposListRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = UserReposListRepositoryImpl(apiService = apiService, mapper = mapper)
    }

    @Test
    fun checkRepositoryReturnsCorrectlyOnSuccess() = runTest {
        val mappedResult =
            listOf(
                Repo(
                    id = 1,
                    name = "name",
                    description = "description",
                    updatedAt = "update date",
                    starsCount = 2,
                    forksCount = 1
                )
            )

        coEvery { apiService.fetchUserRepos(any()) } returns emptyList()

        coEvery { mapper.map(any()) } returns mappedResult

        repository.fetch("").collectLatest { result ->
            val repos = (result as? Result.Success)?.data
            assertEquals(repos, mappedResult)
        }

        coVerifySequence {
            apiService.fetchUserRepos(any())
            mapper.map(any())
        }
    }

    @Test
    fun checkRepositoryReturnsCorrectlyOnException() = runTest {
        coEvery { apiService.fetchUserRepos(any()) } throws Exception("test exception")

        repository.fetch("").collectLatest { result ->
            val message = (result as? Result.Error)?.throwable?.message
            assertEquals("test exception", message)
        }

        coVerifySequence {
            apiService.fetchUserRepos(any())
        }
    }
}