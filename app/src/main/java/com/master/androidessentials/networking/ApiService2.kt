package com.master.androidessentials.networking

import com.master.androidessentials.mvvm.models.response.ApiUser
import retrofit2.Response
import retrofit2.http.GET

//For some other endpoints and base urls apart from main one
interface ApiService2 {

    @GET("users")
    suspend fun getUsersList(): Response<List<ApiUser>>

    @GET("more-users")
    suspend fun getMoreUsers(): Response<List<ApiUser>>
}