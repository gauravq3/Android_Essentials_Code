package com.master.androidessentials.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Employee::class], version = 1)
abstract class EmployeeDatabase:RoomDatabase() {
abstract fun getEmpDao1():EmployeeDao

companion object
{
    private var instance:EmployeeDatabase?=null

    fun getInstance(context:Context):EmployeeDatabase
    {
        return instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                EmployeeDatabase::class.java,
                "my-database"
            ).build()
            instance
        }

    }
}


}