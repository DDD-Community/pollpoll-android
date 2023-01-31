package com.ddd.pollpoll.core.network.module

import com.ddd.pollpoll.core.network.remote.LoginRemoteSource
import com.ddd.pollpoll.core.network.remote.LoginRemoteSourceImp
import com.ddd.pollpoll.core.network.remote.PostRemoteSource
import com.ddd.pollpoll.core.network.remote.PostRemoteSourceImp
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

    @Singleton
    @Binds
    fun bindsPostRemoteSource(
        postRemoteSourceImp: PostRemoteSourceImp
    ): PostRemoteSource
}
