package com.ddd.pollpoll.core.network.module

import com.ddd.pollpoll.core.network.remote.LoginRemoteSource
import com.ddd.pollpoll.core.network.remote.LoginRemoteSourceImp
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