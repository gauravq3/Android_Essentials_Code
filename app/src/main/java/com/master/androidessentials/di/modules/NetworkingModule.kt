package com.master.androidessentials.di.modules

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.master.androidessentials.di.ApplicationContextQualifier
import com.master.androidessentials.di.qualifiers.BASEURL2
import com.master.androidessentials.di.qualifiers.BaseUrl1
import com.master.androidessentials.networking.ApiService
import com.master.androidessentials.networking.ApiService2
import com.master.androidessentials.utils.Constants
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
import javax.inject.Singleton

/*created by Gaurav Singh 23-06-2023*/

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @BaseUrl1
    fun provideBaseUrl1(): String {
        return Constants.BASE_URL1
    }

    @Provides
    @BASEURL2
    fun provideBaseUrl2(): String = Constants.BASE_URL2


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
    @BaseUrl1
    fun provideRetrofit(@BaseUrl1 url: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @BASEURL2
    fun provideRetrofit2(@BASEURL2 url: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()


    @Provides
    fun provideApiService(@BaseUrl1 retrofit1: Retrofit): ApiService =
        retrofit1.create(ApiService::class.java)

    @Provides
    fun provideApiService2(@BASEURL2 retrofit2: Retrofit): ApiService2 =
        retrofit2.create(ApiService2::class.java)

    @Provides
    fun provideGlide(@ApplicationContext application: Context): RequestManager =
        Glide.with(application)

}