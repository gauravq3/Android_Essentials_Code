package com.master.androidessentials.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.master.androidessentials.utils.Address
import com.master.androidessentials.utils.AddressConverter

@Entity(tableName = "employee")
@TypeConverters(AddressConverter::class)
data class Employee(
    @PrimaryKey val empId: Int,
    val empName: String,
    val empEmail: String,
    val empPhone: String,
    val address: Address
)
