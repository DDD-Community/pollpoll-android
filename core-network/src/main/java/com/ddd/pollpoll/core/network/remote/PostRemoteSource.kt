package com.ddd.pollpoll.core.network.remote

import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.PostPostRequest
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import javax.inject.Inject

interface PostRemoteSource {

    suspend fun insertPost(post: PostPostRequest): PostResponse
}

class PostRemoteSourceImp @Inject constructor(
    private val pollAPI: PollAPI
) : PostRemoteSource {
    override suspend fun insertPost(post: PostPostRequest): PostResponse = pollAPI.postPosts(post).executeHandle()
}
