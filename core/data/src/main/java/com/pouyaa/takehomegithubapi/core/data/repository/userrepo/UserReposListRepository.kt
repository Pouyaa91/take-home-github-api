package com.pouyaa.takehomegithubapi.core.data.repository.userrepo

import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.model.Result
import kotlinx.coroutines.flow.Flow

fun interface UserReposListRepository {
    fun fetch(userId: String): Flow<Result<List<Repo>>>
}