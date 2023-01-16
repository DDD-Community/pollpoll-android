package com.ddd.pollpoll.core.network.remote


import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface LoginRemoteSource {

    fun loginGoogle(token: String): String
}

class LoginRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : LoginRemoteSource {
    override fun loginGoogle(token: String): String {
        val result = pollAPI.loginGoogle(token)
        if (result.isSuccessful) return result.body() ?: "못찾았다 꾀꼬리"
        else return throw Exception()
    }
}
