package com.master.androidessentials.networking

import com.master.androidessentials.mvvm.models.response.ApiUser
import com.master.androidessentials.mvvm.models.userslist.UsersList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService2 {

    @GET("users")
    suspend fun getUsersList(): Response<List<ApiUser>>

    @GET("more-users")
    suspend fun getMoreUsers(): Response<List<ApiUser>>
}