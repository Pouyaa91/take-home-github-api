package com.pouyaa.takehomegithubapi.core.data.repository.userrepo

import com.pouyaa.takehomegithubapi.core.data.mapper.NetworkRepoToRepoListMapper
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.network.service.UserReposApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserReposListRepositoryImpl @Inject constructor(
    private val apiService: UserReposApiService,
    private val mapper: NetworkRepoToRepoListMapper
) : UserReposListRepository {
    override fun fetch(userId: String): Flow<Result<List<Repo>>> {
        return flow<Result<List<Repo>>> {
            val result = apiService.fetchUserRepos(userId)
            emit(Result.Success(data = mapper.map(result)))
        }.catch {
            emit(Result.Error(throwable = it))
        }
    }
}