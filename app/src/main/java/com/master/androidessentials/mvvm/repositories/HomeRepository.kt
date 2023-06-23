package com.master.androidessentials.mvvm.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.master.androidessentials.localdb.UserDao
import com.master.androidessentials.mvvm.models.userslist.User
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.networking.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: UserDao
) {

   suspend fun getAllUsers(): LiveData<ApiResponse<List<User>>> {
        val data = MutableLiveData<ApiResponse<List<User>>>()
        data.value = ApiResponse.Loading

        val localData = withContext(Dispatchers.IO)
        {
            dao.getAllUsers()
        }
        if (localData.isNotEmpty()) {
            data.value = ApiResponse.Success(localData)
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getAllUsers()
                    if (response.isSuccessful) {
                        val fetchedData = response.body()?.users ?: emptyList()
                        dao.insertAll(fetchedData)
                        data.postValue(ApiResponse.Success(fetchedData))
                    } else {
                        data.postValue(ApiResponse.Failure("API request failed"))
                    }
                } catch (e: Exception) {
                    data.postValue(ApiResponse.Failure(e.message!!))
                }
            }
        }

        return data
    }
}