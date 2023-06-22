package com.master.androidessentials.networking

import com.master.androidessentials.mvvm.models.userslist.UsersList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
   suspend fun getAllPosts(): Response<UsersList>

}