package com.master.androidessentials.di.modules

import com.master.androidessentials.localdb.UserDao
import com.master.androidessentials.networking.ApiService
import com.master.androidessentials.mvvm.repositories.HomeRepository
import com.master.androidessentials.mvvm.repositories.ParallelCallsRepo
import com.master.androidessentials.mvvm.repositories.SeriesNetworkCallRepo
import com.master.androidessentials.networking.ApiService2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideHomeRepository(apiService: ApiService, empDao: UserDao): HomeRepository =
        HomeRepository(apiService, empDao)

    @Provides
    fun provideSeriesCallRepository(apiService: ApiService2): SeriesNetworkCallRepo =
        SeriesNetworkCallRepo(apiService)

    @Provides
    fun provideParallelCallsRepository(apiService: ApiService2): ParallelCallsRepo =
        ParallelCallsRepo(apiService)
}