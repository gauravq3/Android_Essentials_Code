package com.master.androidessentials.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.master.androidessentials.mvvm.models.userslist.Company

object CompanyConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCompany(address: Company): String {
        return gson.toJson(address)
    }

    @TypeConverter
    fun toCompany(value: String): Company {
        return gson.fromJson(value, Company::class.java)
    }
}