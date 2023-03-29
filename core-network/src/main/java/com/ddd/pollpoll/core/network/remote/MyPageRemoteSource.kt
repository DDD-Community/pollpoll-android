package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.GetCategoriesResponse
import com.ddd.pollpoll.core.network.model.GetMyPageResponse
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface MyPageRemoteSource {
    suspend fun getMyPage(type: String): GetMyPageResponse
}

class MyPageRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : MyPageRemoteSource {
    override suspend fun getMyPage(type: String): GetMyPageResponse = pollAPI.getMyPage(type).executeHandle()
}
