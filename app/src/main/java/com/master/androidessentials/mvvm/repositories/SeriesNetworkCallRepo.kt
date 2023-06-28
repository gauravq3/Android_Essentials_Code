package com.master.androidessentials.mvvm.repositories

import androidx.lifecycle.MutableLiveData
import com.master.androidessentials.mvvm.models.response.ApiUser
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.networking.ApiService
import javax.inject.Inject

class SeriesNetworkCallRepo @Inject constructor(private val api: ApiService) {
    suspend fun getUsers(): ApiResponse<List<ApiUser>> {
        val data = MutableLiveData<ApiResponse<List<ApiUser>>>(ApiResponse.Loading)
        try {
            val response = api.getUsersList()
            if (response.isSuccessful) {
                val fetchedData = response.body() ?: emptyList()
                data.value=(ApiResponse.Success(fetchedData))
            } else {
                data.value=(ApiResponse.Failure("API request failed"))
            }
        } catch (e: Exception) {
            data.value=(ApiResponse.Failure(e.message!!))
        }
       return data.value!!
    }

  suspend  fun getMoreUsers(): ApiResponse<List<ApiUser>> {
      val data = MutableLiveData<ApiResponse<List<ApiUser>>>(ApiResponse.Loading)
      try {
          val response = api.getMoreUsers()
          if (response.isSuccessful) {
              val fetchedData = response.body() ?: emptyList()
              data.value=(ApiResponse.Success(fetchedData))
          } else {
              data.value=(ApiResponse.Failure("API request failed"))
          }
      } catch (e: Exception) {
          data.value=(ApiResponse.Failure(e.message!!))
      }
     return data.value!!
    }


}