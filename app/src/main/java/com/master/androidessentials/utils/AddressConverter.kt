package com.master.androidessentials.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

object AddressConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromAddress(address: Address): String {
        return gson.toJson(address)
    }

    @TypeConverter
    fun toAddress(value: String): Address {
        return gson.fromJson(value, Address::class.java)
    }
}

data class Address(
    val street: String,
    val city: String,
    val state: String
)