package com.master.androidessentials.di

import android.content.Context
import androidx.room.Room
import com.master.androidessentials.localdb.EmployeeDao
import com.master.androidessentials.localdb.EmployeeDatabase
import dagger.Module
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context): EmployeeDatabase {
        return Room.databaseBuilder(application, EmployeeDatabase::class.java, "database").build()
    }

    @Provides
    @Singleton
    fun provideDao(roomDatabase: EmployeeDatabase): EmployeeDao {

        return roomDatabase.getEmpDao1()

    }

}