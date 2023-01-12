package com.pollpoll.core_network.module

import com.pollpoll.core_network.HttpRequestInterceptor
import com.pollpoll.core_network.retrofit.PollAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .connectTimeout(3, TimeUnit.SECONDS)
            .callTimeout(3, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://15.164.186.97")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePollAPI(
        retrofit: Retrofit,
    ): PollAPI =
        retrofit.create(PollAPI::class.java)

}