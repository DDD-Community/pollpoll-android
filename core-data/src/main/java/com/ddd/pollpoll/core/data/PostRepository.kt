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
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.Vote
import com.ddd.pollpoll.core.data.model.asExternalModel
import com.ddd.pollpoll.core.network.model.PollItem
import com.ddd.pollpoll.core.network.model.PostPostRequest
import com.ddd.pollpoll.core.network.model.PutVoteRequest
import com.ddd.pollpoll.core.network.remote.PostRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PostRepository {
    suspend fun insertPost(token: Vote): Flow<Unit>
    suspend fun putPoll(pollId: Int, pollItemIds: PutVoteRequest): Flow<Unit>
    suspend fun getPosts(lastPostId: Int? = null, keyword: String? = null): Flow<List<Post>>
    suspend fun getPost(postId: Int): Flow<Post>

    suspend fun getPopularPost(): Flow<PopularPost>
}

class PostRepositoryImp @Inject constructor(
    private val postRemoteSource: PostRemoteSource,
) : PostRepository {

    override suspend fun insertPost(vote: Vote): Flow<Unit> = flow {
        emit(
            postRemoteSource.insertPost(
                PostPostRequest(
                    categoryId = vote.category.categoryId,
                    contents = vote.contents,
                    milliseconds = vote.milliseconds,
                    multipleChoice = vote.multipleChoice,
                    pollItems = vote.pollItems.map { PollItem(it.name) },
                    title = vote.title,
                ),
            ),
        )
    }

    override suspend fun putPoll(pollId: Int, pollItemIds: PutVoteRequest): Flow<Unit> = flow {
        emit(postRemoteSource.putPoll(pollId, pollItemIds))
    }

    override suspend fun getPosts(lastPostId: Int?, keyword: String?): Flow<List<Post>> = flow {
        val result = postRemoteSource.getPosts(lastPostId, keyword).asExternalModel()
        emit(result)
    }

    override suspend fun getPost(postId: Int): Flow<Post> = flow {
        val result = postRemoteSource.getPost(postId).asExternalModel()
        emit(result)
    }

    override suspend fun getPopularPost(): Flow<PopularPost> = flow {
        val result = postRemoteSource.getPopularPosts().asExternalModel()
        emit(result)
    }
}
