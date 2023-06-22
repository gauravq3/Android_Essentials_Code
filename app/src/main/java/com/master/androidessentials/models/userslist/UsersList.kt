package com.master.androidessentials.models.userslist

data class UsersList(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
)