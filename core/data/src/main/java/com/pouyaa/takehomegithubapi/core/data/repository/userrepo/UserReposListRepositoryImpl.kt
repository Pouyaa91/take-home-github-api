package com.pouyaa.takehomegithubapi.core.data.repository.userrepo

import com.pouyaa.takehomegithubapi.core.data.mapper.NetworkRepoToRepoListMapper
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.network.model.NetworkMessage
import com.pouyaa.takehomegithubapi.core.network.service.UserReposApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserReposListRepositoryImpl @Inject constructor(
    private val apiService: UserReposApiService,
    private val mapper: NetworkRepoToRepoListMapper,
    private val json: Json
) : UserReposListRepository {
    override fun fetch(userId: String): Flow<Result<List<Repo>>> {
        return flow {
            val result = apiService.fetchUserRepos(userId)
            if (result.isSuccessful) {
                emit(Result.Success(data = mapper.map(result.body())))
            } else {
                val networkMessage =
                    json.decodeFromString<NetworkMessage>(result.errorBody()?.string().orEmpty())
                emit(Result.Error(message = networkMessage.message, throwable = null))
            }
        }.catch {
            emit(Result.Error(throwable = it, message = null))
        }
    }
}