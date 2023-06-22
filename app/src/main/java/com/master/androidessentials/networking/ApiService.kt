package com.master.androidessentials.networking

import com.master.androidessentials.models.userslist.UsersList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
   suspend fun getAllPosts(): Response<UsersList>

    @GET("posts")
    suspend fun refreshToken(): Response<UsersList>
}