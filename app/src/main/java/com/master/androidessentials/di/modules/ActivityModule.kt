package com.master.androidessentials.di.modules

import android.app.Activity
import com.master.androidessentials.localdb.UserDao
import com.master.androidessentials.networking.ApiService
import com.master.androidessentials.mvvm.repositories.HomeRepository
import com.master.androidessentials.mvvm.repositories.ParallelCallsRepo
import com.master.androidessentials.mvvm.repositories.SeriesNetworkCallRepo
import com.master.androidessentials.networking.ApiService2
import com.master.androidessentials.utils.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext


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
    @Provides
    fun providePermissionManager(@ActivityContext activity: Activity): PermissionManager {
        return PermissionManager(activity)
    }
}