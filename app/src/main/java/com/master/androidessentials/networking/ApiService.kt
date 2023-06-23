package com.master.androidessentials.networking

import com.master.androidessentials.mvvm.models.userslist.UsersList
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users")
    suspend fun getAllUsers(): Response<UsersList>

    @FormUrlEncoded
    @POST("newPost")
    suspend fun addNewPost(@Field("id") id: Int): Response<UsersList>

}