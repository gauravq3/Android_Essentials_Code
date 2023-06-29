package com.master.androidessentials.mvvm.repositories

import com.master.androidessentials.di.qualifiers.BaseUrl1
import com.master.androidessentials.localdb.UserDao
import com.master.androidessentials.mvvm.models.userslist.User
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.networking.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
     private val apiService: ApiService,
    private val dao: UserDao
) {

   suspend fun getAllUsers(): StateFlow<ApiResponse<List<User>>> {
        val data = MutableStateFlow<ApiResponse<List<User>>>(ApiResponse.Loading)
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
                        data.value=(ApiResponse.Success(fetchedData))
                    } else {
                        data.value=(ApiResponse.Failure("API request failed"))
                    }
                } catch (e: Exception) {
                    data.value=(ApiResponse.Failure(e.message!!))
                }
            }
        }

        return data
    }
}