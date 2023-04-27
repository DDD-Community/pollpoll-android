package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.GetMyPageResponse
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface MyPageRemoteSource {
    suspend fun getMyPage(lastPostId: Int?, type: String): GetMyPageResponse
}

class MyPageRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI,
) : MyPageRemoteSource {
    override suspend fun getMyPage(lastPostId: Int?, type: String): GetMyPageResponse = pollAPI.getMyPage(lastPostId = lastPostId, type).executeHandle()
}
