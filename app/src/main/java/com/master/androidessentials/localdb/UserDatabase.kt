package com.master.androidessentials.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.master.androidessentials.mvvm.models.userslist.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase:RoomDatabase() {
abstract fun getEmpDao1():UserDao

companion object
{
    private var instance:UserDatabase?=null

    fun getInstance(context:Context):UserDatabase
    {
        return instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "my-database"
            ).build()
            instance
        }

    }
}


}