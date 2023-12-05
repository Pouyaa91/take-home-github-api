package com.pouyaa.takehomegithubapi.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaa.takehomegithubapi.core.domain.usecase.GetUserReposListUseCase
import com.pouyaa.takehomegithubapi.core.domain.usecase.GetUserUseCase
import com.pouyaa.takehomegithubapi.core.model.Repo
import com.pouyaa.takehomegithubapi.core.model.Result
import com.pouyaa.takehomegithubapi.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserReposListUseCase: GetUserReposListUseCase
) : ViewModel() {

    var userUiState by mutableStateOf<UserUiState>(UserUiState.Init)
        private set

    var reposUiState by mutableStateOf<ReposUiState>(ReposUiState.Init)
        private set

    fun onSearchCLicked(userId: String) {
        fetchUser(userId)
    }

    private fun fetchUser(userId: String) {
        viewModelScope.launch {
            userUiState = UserUiState.Loading
            getUserUseCase.fetch(userId).collectLatest { result ->
                when (result) {
                    is Result.Success -> onReceivedUser(result.data)
                    is Result.Error -> userUiState =
                        UserUiState.Error(message = result.throwable?.message)
                }
            }
        }
    }

    private fun onReceivedUser(user: User) {
        userUiState = UserUiState.Success(user = user)
        fetchUserRepos(user.userId)
    }

    private fun fetchUserRepos(userId: String) {
        viewModelScope.launch {
            getUserReposListUseCase.fetch(userId).collectLatest { result ->
                reposUiState = when (result) {
                    is Result.Success -> ReposUiState.Success(repos = result.data)
                    is Result.Error -> ReposUiState.Error(message = result.throwable?.message)
                }

            }
        }
    }

    sealed class UserUiState {
        data object Init : UserUiState()
        data object Loading : UserUiState()
        data class Success(val user: User) : UserUiState()
        data class Error(val message: String?) : UserUiState()
    }

    sealed class ReposUiState {
        data object Init : ReposUiState()
        data class Success(val repos: List<Repo>) : ReposUiState()
        data class Error(val message: String?) : ReposUiState()
    }
}