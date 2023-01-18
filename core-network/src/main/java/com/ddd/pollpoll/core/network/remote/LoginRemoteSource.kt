package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.LoginRequest
import com.ddd.pollpoll.core.network.model.LoginResponse
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface LoginRemoteSource {

    suspend fun loginGoogle(token: LoginRequest): LoginResponse
}

class LoginRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : LoginRemoteSource {
    override suspend fun loginGoogle(token: LoginRequest): LoginResponse = pollAPI.loginGoogle(token).executeHandle()
}
