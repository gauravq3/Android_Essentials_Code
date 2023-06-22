package com.master.androidessentials.repositories

import android.util.Log
import com.master.androidessentials.models.userslist.UsersList
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.networking.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(val apiService: ApiService) {


    suspend fun getAllPosts(): ApiResponse<UsersList> {
        try {
            val response = apiService.getAllPosts()
            Log.e("getData", response.toString())
            if (response.isSuccessful) {


                return ApiResponse.Success(response.body()!!)
            } else {
                return ApiResponse.Failure(response.message())
            }
        } catch (e: Exception) {
            return ApiResponse.Failure(e.message ?: "An error occurred")
        }

    }
}