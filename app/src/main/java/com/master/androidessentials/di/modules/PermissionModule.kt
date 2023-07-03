package com.master.androidessentials.di.modules

import androidx.fragment.app.Fragment
import com.master.androidessentials.utils.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object PermissionModule {

    @Provides
    fun providePermissionManager(fragment: Fragment): PermissionManager {
        return PermissionManager(fragment)
    }
}
