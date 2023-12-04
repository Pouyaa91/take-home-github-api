package com.pouyaa.takehomegithubapi.core.data.repository.user

import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.model.User
import kotlinx.coroutines.flow.Flow

fun interface UserRepository {
    fun fetch(userId: String): Flow<Result<User>>
}