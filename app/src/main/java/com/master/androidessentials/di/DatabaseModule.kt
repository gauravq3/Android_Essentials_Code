package com.master.androidessentials.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.master.androidessentials.localdb.EmployeeDao
import com.master.androidessentials.localdb.EmployeeDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    fun provideDatabase(application: Application): RoomDatabase {
        return Room.databaseBuilder(application, EmployeeDatabase::class.java, "database").build()
    }

    fun provideDao(roomDatabase: EmployeeDatabase): EmployeeDao {

        return roomDatabase.getEmpDao1()

    }

}