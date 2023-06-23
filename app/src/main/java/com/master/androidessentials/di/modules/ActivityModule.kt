package com.master.androidessentials.di.modules

import com.master.androidessentials.localdb.UserDao
import com.master.androidessentials.networking.ApiService
import com.master.androidessentials.mvvm.repositories.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideHomeRepository(apiService: ApiService,empDao: UserDao): HomeRepository = HomeRepository(apiService,empDao)
}