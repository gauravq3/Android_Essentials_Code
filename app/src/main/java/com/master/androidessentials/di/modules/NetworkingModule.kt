package com.master.androidessentials.di.modules

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.master.androidessentials.di.ApplicationContextQualifier
import com.master.androidessentials.networking.ApiService
import com.master.androidessentials.utils.Constants.BASE_URL
import com.master.androidessentials.utils.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*created by Gaurav Singh 23-06-2023*/

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @ApplicationContextQualifier
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideInterceptor(application: Context): Interceptor = NetworkInterceptor(application)


    @Provides
    fun provideOkHttp(interceptor: NetworkInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(logging).build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()


    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Provides
    fun provideGlide(@ApplicationContext application: Context): RequestManager =
        Glide.with(application)

}