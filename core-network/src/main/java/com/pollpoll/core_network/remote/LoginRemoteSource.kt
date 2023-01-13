package com.pollpoll.core_network.remote

import com.pollpoll.core_network.retrofit.PollAPI
import javax.inject.Inject

interface LoginRemoteSource {

    fun loginGoogle(token: String): String
}

class LoginRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : LoginRemoteSource {
    override fun loginGoogle(token: String): String = pollAPI.loginGoogle(token)
}
