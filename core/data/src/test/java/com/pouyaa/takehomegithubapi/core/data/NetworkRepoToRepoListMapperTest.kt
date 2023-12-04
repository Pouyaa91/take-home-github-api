package com.pouyaa.takehomegithubapi.core.data

import com.pouyaa.takehomegithubapi.core.data.mapper.NetworkRepoToRepoListMapper
import com.pouyaa.takehomegithubapi.core.network.model.NetworkRepo
import org.junit.Assert.assertEquals
import org.junit.Test


class NetworkRepoToRepoListMapperTest {

    private val mapper = NetworkRepoToRepoListMapper()

    @Test
    fun testMapperWorksCorrectly() {
        val networkReposList =
            listOf(
                NetworkRepo(
                    id = 0,
                    name = "test name",
                    description = "test description",
                    updatedAt = "2023",
                    starsCount = 10,
                    forksCount = 2
                )
            )

        val networkRepo = networkReposList.firstOrNull()

        val mappedData = mapper.map(networkReposList)

        val mappedRepo = mappedData.firstOrNull()

        assertEquals(mappedRepo?.id, networkRepo?.id)
        assertEquals(mappedRepo?.name, networkRepo?.name)
        assertEquals(mappedRepo?.description, networkRepo?.description)
        assertEquals(mappedRepo?.updatedAt, networkRepo?.updatedAt)
        assertEquals(mappedRepo?.starsCount, networkRepo?.starsCount)
        assertEquals(mappedRepo?.forksCount, networkRepo?.forksCount)
    }
}