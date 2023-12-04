package com.pouyaa.takehomegithubapi.core.data.repository.user

import com.pouyaa.takehomegithubapi.core.data.mapper.NetworkUserToUserMapper
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.model.User
import com.pouyaa.takehomegithubapi.core.network.service.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val mapper: NetworkUserToUserMapper
) : UserRepository {
    override fun fetch(userId: String): Flow<Result<User>> {
        return flow<Result<User>> {
            val result = apiService.fetchUser(userId)
            emit(Result.Success(data = mapper.map(result)))
        }.catch {
            emit(Result.Error(throwable = it))
        }
    }
}