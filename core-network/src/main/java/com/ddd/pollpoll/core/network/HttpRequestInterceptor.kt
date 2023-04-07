package com.ddd.pollpoll.core.network

import com.ddd.pollpoll.datastore.PollPollDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

// TODO: 시간 많을때 인터셉터를 동적으로 만들어야함.

private val noneHeaderUrlList = listOf("google-login", "popular")

class HttpRequestInterceptor(private val pollPreference: PollPollDataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            pollPreference.getToken().first()
        }

        val originalRequest = chain.request()
        val request = if (((originalRequest.url.toString()).split("/").last() in noneHeaderUrlList)
        ) {
            originalRequest.newBuilder().url(originalRequest.url)
                .build()
        } else {
            originalRequest.newBuilder().url(originalRequest.url)
                .header("Authorization", "Bearer $token")
                .build()
        }

        val response = chain.proceed(request)
        return response
    }
}
