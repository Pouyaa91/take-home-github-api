package com.pouyaa.takehomegithubapi.core.data

import com.pouyaa.takehomegithubapi.core.data.mapper.NetworkUserToUserMapper
import com.pouyaa.takehomegithubapi.core.network.model.NetworkUser
import org.junit.Assert.assertEquals
import org.junit.Test


class NetworkUserToUserMapperTest {

    private val mapper = NetworkUserToUserMapper()

    @Test
    fun testMapperWorksCorrectly() {
        val networkUser =
            NetworkUser(userId = "test id", name = "test name", avatarUrl = "avatar url")

        val mappedData = mapper.map(networkUser)

        assertEquals(mappedData.name, networkUser.name)
        assertEquals(mappedData.userId, networkUser.userId)
        assertEquals(mappedData.avatarUrl, networkUser.avatarUrl)
    }
}