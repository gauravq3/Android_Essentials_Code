package com.master.androidessentials.mvvm.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.master.androidessentials.localdb.EmployeeDao
import com.master.androidessentials.mvvm.models.userslist.User
import com.master.androidessentials.mvvm.models.userslist.UsersList
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.networking.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: EmployeeDao
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


//    suspend fun getAllUsers(): ApiResponse<UsersList> {
//        return try {
//            val response = apiService.getAllUsers()
//            Log.e("getData", response.toString())
//            if (response.isSuccessful) {
//             withContext(Dispatchers.IO)
//             {
//                  dao.insertAll(response.body()!!.users)
//             }
//                ApiResponse.Success(response.body()!!)
//
//            } else {
//                ApiResponse.Failure(response.message())
//            }
//        } catch (e: Exception) {
//            ApiResponse.Failure(e.message ?: "An error occurred")
//        }
//
//    }

    suspend fun getUsers(): List<User> {
        // Retrieve data from the Room database
        return dao.getAllUsers()
    }
}