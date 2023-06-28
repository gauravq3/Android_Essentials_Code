package com.master.androidessentials.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master.androidessentials.mvvm.models.response.ApiUser
import com.master.androidessentials.mvvm.repositories.SeriesNetworkCallRepo
import com.master.androidessentials.networking.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesNetworkCallsViewModel @Inject constructor(private val repo:SeriesNetworkCallRepo):ViewModel() {
    private val uiState = MutableLiveData<ApiResponse<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(ApiResponse.Loading)
            try {
                val usersFromApi = repo.getUsers()
                when(usersFromApi) {
                    is ApiResponse.Success -> {
                        uiState.postValue(ApiResponse.Success(usersFromApi.data))
                    }
                    is ApiResponse.Failure -> {
                        uiState.postValue(usersFromApi)
                    }
                    is ApiResponse.Loading -> {
                        uiState.postValue(ApiResponse.Loading)
                    }
                }

            } catch (e: Exception) {
                uiState.postValue(ApiResponse.Failure("Something Went Wrong"))
            }

            try {
                val usersFromApi = repo.getMoreUsers()
                when(usersFromApi) {
                    is ApiResponse.Success -> {
                        uiState.postValue(ApiResponse.Success(usersFromApi.data))
                    }
                    is ApiResponse.Failure -> {
                        uiState.postValue(usersFromApi)
                    }
                    is ApiResponse.Loading -> {
                        uiState.postValue(ApiResponse.Loading)
                    }
                }

            } catch (e: Exception) {
                uiState.postValue(ApiResponse.Failure("Something Went Wrong"))
            }






        }
    }

    fun getUiState(): LiveData<ApiResponse<List<ApiUser>>> {
        return uiState
    }
}