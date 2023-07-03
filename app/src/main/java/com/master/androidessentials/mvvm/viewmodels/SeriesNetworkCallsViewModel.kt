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
    private val usersState = MutableLiveData<ApiResponse<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            usersState.postValue(ApiResponse.Loading)
            try {
                when(val usersFromApi = repo.getUsers()) {
                    is ApiResponse.Success -> {
                        usersState.postValue(ApiResponse.Success(usersFromApi.data))
                    }
                    is ApiResponse.Failure -> {
                        usersState.postValue(usersFromApi)
                    }
                    is ApiResponse.Loading -> {
                        usersState.postValue(ApiResponse.Loading)
                    }
                }

            } catch (e: Exception) {
                usersState.postValue(ApiResponse.Failure("Something Went Wrong"))
            }

            try {
                when(val usersFromApi = repo.getMoreUsers()) {
                    is ApiResponse.Success -> {
                        usersState.postValue(ApiResponse.Success(usersFromApi.data))
                    }
                    is ApiResponse.Failure -> {
                        usersState.postValue(usersFromApi)
                    }
                    is ApiResponse.Loading -> {
                        usersState.postValue(ApiResponse.Loading)
                    }
                }

            } catch (e: Exception) {
                usersState.postValue(ApiResponse.Failure("Something Went Wrong"))
            }






        }
    }

    fun getUsersData(): LiveData<ApiResponse<List<ApiUser>>> {
        return usersState
    }
}