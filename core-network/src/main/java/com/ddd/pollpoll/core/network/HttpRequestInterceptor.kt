package com.ddd.pollpoll.core.network

import com.ddd.pollpoll.datastore.PollPollDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor(private val pollPreference: PollPollDataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            pollPreference.getToken().first()
        }

        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url)
            .header("Authorization", token)
            .build()
        val response = chain.proceed(request)
        return response
    }
}
