package com.pouyaa.takehomegithubapi.core.domain.usecase

import com.pouyaa.takehomegithubapi.core.data.repository.userrepo.UserReposListRepository
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReposListUseCase @Inject constructor(
    private val repository: UserReposListRepository
) {
    fun fetch(userId: String): Flow<Result<List<Repo>>> = repository.fetch(userId)
}