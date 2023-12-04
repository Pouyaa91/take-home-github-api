package com.pouyaa.takehomegithubapi.core.data.di

import com.pouyaa.takehomegithubapi.core.data.repository.user.UserRepository
import com.pouyaa.takehomegithubapi.core.data.repository.user.UserRepositoryImpl
import com.pouyaa.takehomegithubapi.core.data.repository.userrepo.UserReposListRepository
import com.pouyaa.takehomegithubapi.core.data.repository.userrepo.UserReposListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun provideUserReposListRepository(impl: UserReposListRepositoryImpl): UserReposListRepository
}