package com.pouyaa.takehomegithubapi.core.model

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val throwable: Throwable?, val message: String?) : Result<Nothing>
}