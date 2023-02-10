/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ddd.pollpoll.core.data

import com.ddd.pollpoll.PopularPost
import com.ddd.pollpoll.Vote
import com.ddd.pollpoll.core.network.model.GetPostResponse
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.core.network.model.asNetworkModel
import com.ddd.pollpoll.core.network.remote.PostRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PostRepository {
    suspend fun insertPost(token: Vote): Flow<Unit>
    suspend fun getPosts(lastPostId: Int): Flow<GetPostResponse>
    suspend fun getPost(postId: Int): Flow<PostResponse>

    suspend fun getPopularPost(): Flow<PopularPost>
}

class PostRepositoryImp @Inject constructor(
    private val postRemoteSource: PostRemoteSource
) : PostRepository {
    override suspend fun insertPost(vote: Vote): Flow<Unit> = flow {
        emit(postRemoteSource.insertPost(vote.asNetworkModel()))
    }

    override suspend fun getPosts(lastPostId: Int): Flow<GetPostResponse> = flow {
        val result = postRemoteSource.getPosts(lastPostId)
        emit(result)
    }

    override suspend fun getPost(postId: Int): Flow<PostResponse> = flow {
        val result = postRemoteSource.getPost(postId)
        emit(result)
    }

    override suspend fun getPopularPost(): Flow<PopularPost> = flow {
        val result = postRemoteSource.getPopularPosts()
        emit(result)
    }
}
