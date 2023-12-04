package com.pouyaa.takehomegithubapi.core.data.mapper

import com.pouyaa.takehomegithubapi.core.model.User
import com.pouyaa.takehomegithubapi.core.network.model.NetworkUser
import javax.inject.Inject

class NetworkUserToUserMapper @Inject constructor() {
    fun map(input: NetworkUser): User {
        return User(
            name = input.name.orEmpty(),
            userId = input.userId.orEmpty(),
            avatarUrl = input.avatarUrl.orEmpty()
        )
    }
}