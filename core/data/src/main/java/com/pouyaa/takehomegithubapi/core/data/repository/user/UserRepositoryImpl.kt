package com.pouyaa.takehomegithubapi.core.data.repository.user

import com.pouyaa.takehomegithubapi.core.data.mapper.NetworkUserToUserMapper
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.model.User
import com.pouyaa.takehomegithubapi.core.network.model.NetworkMessage
import com.pouyaa.takehomegithubapi.core.network.service.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val mapper: NetworkUserToUserMapper,
    private val json: Json
) : UserRepository {
    override fun fetch(userId: String): Flow<Result<User>> {
        return flow {
            val result = apiService.fetchUser(userId)
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