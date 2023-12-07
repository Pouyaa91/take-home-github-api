package com.pouyaa.takehomegithubapi.core.data.mapper

import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.network.model.NetworkRepo
import javax.inject.Inject

class NetworkRepoToRepoListMapper @Inject constructor() {
    fun map(input: List<NetworkRepo>?): List<Repo> {
        return input?.map { networkRepo ->
            Repo(
                id = networkRepo.id ?: -1,
                name = networkRepo.name.orEmpty(),
                description = networkRepo.description.orEmpty(),
                updatedAt = networkRepo.updatedAt
                    ?.replace(Regex("[a-zA-Z]"), " ")
                    ?.trimEnd()
                    .orEmpty(),
                starsCount = networkRepo.starsCount ?: 0,
                forksCount = networkRepo.forksCount ?: 0
            )
        } ?: emptyList()
    }
}