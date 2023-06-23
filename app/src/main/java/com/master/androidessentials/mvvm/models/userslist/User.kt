package com.master.androidessentials.mvvm.models.userslist

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.master.androidessentials.utils.CompanyConverter


@Entity(tableName = "user")
@TypeConverters(CompanyConverter::class)
data class User(
    val company: Company,
    val email: String,
    val firstName: String,
    @PrimaryKey val id: Int,
    val image: String,
    val lastName: String,
    val phone: String,
)