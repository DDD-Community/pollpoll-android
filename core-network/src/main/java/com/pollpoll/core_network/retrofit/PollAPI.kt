package com.pollpoll.core_network.retrofit

import retrofit2.http.Field
import retrofit2.http.POST

interface PollAPI {

    @POST("/api/auth/google-login")
    fun loginGoogle(@Field("idToken") token: String): String
}