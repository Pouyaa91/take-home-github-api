package com.pouyaa.takehomegithubapi.core.domain.usecase

import com.pouyaa.takehomegithubapi.core.data.repository.user.UserRepository
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    fun fetch(userId: String): Flow<Result<User>> = repository.fetch(userId)
}