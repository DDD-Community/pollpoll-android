package com.ddd.pollpoll.core.network.retrofit

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PollAPI {

    @FormUrlEncoded
    @POST("/api/auth/google-login")
    fun loginGoogle(@Field("idToken") token: String): Response<String>
}
