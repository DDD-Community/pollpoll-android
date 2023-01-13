package com.pollpoll.core_network.module

import com.pollpoll.core_network.remote.LoginRemoteSource
import com.pollpoll.core_network.remote.LoginRemoteSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {

    @Singleton
    @Binds
    fun bindsLoginRemoteSource(
        loginRemoteSource: LoginRemoteSourceImp
    ): LoginRemoteSource
}