package com.pouyaa.takehomegithubapi.core.domain

import com.pouyaa.takehomegithubapi.core.data.repository.user.UserRepository
import com.pouyaa.takehomegithubapi.core.domain.usecase.GetUserUseCase
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.model.User
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

class GetUserUseCaseTest {

    @MockK
    lateinit var repository: UserRepository

    private lateinit var useCase: GetUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetUserUseCase(repository = repository)
    }

    @Test
    fun checkUseCaseReturnsCorrectlyOnSuccess() = runTest {
        val mockedUser = User(userId = "id", name = "name", avatarUrl = "image url")

        coEvery { repository.fetch(any()) } returns flowOf(Result.Success(data = mockedUser))

        useCase.fetch("").collectLatest { result ->
            val user = (result as? Result.Success)?.data
            assertEquals(user, mockedUser)
        }

        coVerifySequence {
            repository.fetch(any())
        }
    }

    @Test
    fun checkUseCaseReturnCorrectlyOnError() = runTest {
        coEvery { repository.fetch(any()) } returns flowOf(Result.Error(throwable = Exception("test exception")))

        useCase.fetch("").collectLatest { result ->
            val message = (result as? Result.Error)?.throwable?.message

            assertEquals("test exception", message)
        }

        coVerifySequence {
            repository.fetch(any())
        }
    }
}