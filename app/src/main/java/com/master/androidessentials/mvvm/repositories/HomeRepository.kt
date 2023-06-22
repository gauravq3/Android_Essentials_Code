package com.master.androidessentials.mvvm.repositories

import android.util.Log
import com.master.androidessentials.localdb.Employee
import com.master.androidessentials.localdb.EmployeeDao
import com.master.androidessentials.localdb.EmployeeDatabase
import com.master.androidessentials.mvvm.models.userslist.User
import com.master.androidessentials.mvvm.models.userslist.UsersList
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.networking.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: EmployeeDao
) {

    suspend fun getAllPosts(): ApiResponse<UsersList> {
        return try {
            val response = apiService.getAllPosts()
            Log.e("getData", response.toString())
            if (response.isSuccessful) {
                ApiResponse.Success(response.body()!!)
            } else {
                ApiResponse.Failure(response.message())
            }
        } catch (e: Exception) {
            ApiResponse.Failure(e.message ?: "An error occurred")
        }

    }

    suspend fun getLocalEmpData(): List<Employee> {
        // Retrieve data from the Room database
        return dao.getAllEmployees()
    }
}