package com.master.androidessentials.di

import com.master.androidessentials.networking.ApiService
import com.master.androidessentials.utils.Constants.BASE_URL
import com.master.androidessentials.utils.CustomInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

//    @Provides
//    @Singleton
//    fun provideInterceptor(): Interceptor {
//        return CustomInterceptor()
//    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
        return OkHttpClient.Builder().build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}