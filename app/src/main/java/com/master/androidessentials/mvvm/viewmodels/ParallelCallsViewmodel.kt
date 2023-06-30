package com.master.androidessentials.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master.androidessentials.mvvm.models.response.ApiUser
import com.master.androidessentials.mvvm.repositories.ParallelCallsRepo
import com.master.androidessentials.networking.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParallelCallsViewmodel @Inject constructor(val repository: ParallelCallsRepo) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        data.postValue(ApiResponse.Failure(throwable.message!!))
    }

    val data = MutableLiveData<ApiResponse<List<ApiUser>>>(ApiResponse.Loading)

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(exceptionHandler) {
            data.postValue(ApiResponse.Loading)
            try {
                // coroutineScope is needed, else in case of any network error, it will crash
                coroutineScope {
                    val usersFromApiDeferred = async { repository.getUsers() }
                    val moreUsersFromApiDeferred = async { repository.getMoreUsers() }
                    val allUsersFromApi = mutableListOf<ApiUser>()

                    when (val usersFromApi = usersFromApiDeferred.await()) {
                        is ApiResponse.Success -> {
                            allUsersFromApi.addAll(usersFromApi.data)

                        }
                        is ApiResponse.Failure -> {
                            data.postValue(ApiResponse.Failure(usersFromApi.error))
                        }
                        is ApiResponse.Loading -> {
                            data.postValue(ApiResponse.Loading)
                        }
                    }
                    when (val moreUsersFromApi = moreUsersFromApiDeferred.await()) {
                        is ApiResponse.Success -> {
                            allUsersFromApi.addAll(moreUsersFromApi.data)

                        }
                        is ApiResponse.Failure -> {
                            data.postValue(ApiResponse.Failure(moreUsersFromApi.error))
                        }
                        is ApiResponse.Loading -> {
                            data.postValue(ApiResponse.Loading)
                        }
                    }
                    data.postValue(ApiResponse.Success(allUsersFromApi))


                    data.postValue(ApiResponse.Success(allUsersFromApi))
                }
            } catch (e: Exception) {
                data.postValue(ApiResponse.Failure("Something Went Wrong"))
            }
        }
    }


    fun getData(): LiveData<ApiResponse<List<ApiUser>>> {
        return data
    }
}