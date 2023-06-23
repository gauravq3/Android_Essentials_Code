package com.master.androidessentials.di.modules

import android.content.Context
import androidx.room.Room
import com.master.androidessentials.localdb.UserDao
import com.master.androidessentials.localdb.UserDatabase
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
    fun provideDatabase(@ApplicationContext application: Context): UserDatabase
    =Room.databaseBuilder(application, UserDatabase::class.java, "database").build()

    @Provides
    fun provideDao(roomDatabase: UserDatabase): UserDao = roomDatabase.getEmpDao1()

}