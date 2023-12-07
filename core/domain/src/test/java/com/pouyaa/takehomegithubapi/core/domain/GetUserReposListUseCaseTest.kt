package com.pouyaa.takehomegithubapi.core.domain

import com.pouyaa.takehomegithubapi.core.data.repository.userrepo.UserReposListRepository
import com.pouyaa.takehomegithubapi.core.domain.usecase.GetUserReposListUseCase
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.model.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUserReposListUseCaseTest {

    @MockK
    lateinit var repository: UserReposListRepository

    private lateinit var useCase: GetUserReposListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetUserReposListUseCase(repository = repository)
    }

    @Test
    fun checkUseCaseReturnsCorrectlyOnSuccess() = runTest {
        val mockedUserRepos =
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

        coEvery { repository.fetch(any()) } returns flowOf(Result.Success(data = mockedUserRepos))

        useCase.fetch("").collectLatest { result ->
            val repos = (result as? Result.Success)?.data
            assertEquals(repos, mockedUserRepos)
        }

        coVerifySequence {
            repository.fetch(any())
        }
    }

    @Test
    fun checkUseCaseReturnCorrectlyOnError() = runTest {
        coEvery { repository.fetch(any()) } returns flowOf(
            Result.Error(
                throwable = Exception("test exception"),
                message = null
            )
        )

        useCase.fetch("").collectLatest { result ->
            val message = (result as? Result.Error)?.throwable?.message

            assertEquals("test exception", message)
        }

        coVerifySequence {
            repository.fetch(any())
        }
    }
}