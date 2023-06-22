package com.master.androidessentials.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emp")
data class Employee(@PrimaryKey val empId:Int)
