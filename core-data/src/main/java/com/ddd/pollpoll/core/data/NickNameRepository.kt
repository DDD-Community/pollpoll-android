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

import com.ddd.pollpoll.HasNickname
import com.ddd.pollpoll.Nickname
import com.ddd.pollpoll.core.data.model.asExternalModel
import com.ddd.pollpoll.core.network.model.*
import com.ddd.pollpoll.core.network.remote.NickNameRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface NickNameRepository {
    suspend fun getNickNameRecommend(): Flow<Nickname>

    suspend fun putNickname(nickname: Nickname): Flow<Unit>

    suspend fun getHasNickname(): Flow<HasNickname>
}

class NickNameRepositoryImp @Inject constructor(
    private val nickNameRemoteSource: NickNameRemoteSource,
) : NickNameRepository {
    override suspend fun getNickNameRecommend(): Flow<Nickname> = flow {
        val result = nickNameRemoteSource.getNickNameRecommend().asExternalModel()
        emit(result)
    }

    override suspend fun putNickname(nickname: Nickname): Flow<Unit> = flow {
        emit(nickNameRemoteSource.putNickname(PutNicknameRequest(nickname.nickname)))
    }

    override suspend fun getHasNickname(): Flow<HasNickname> = flow {
        val result = nickNameRemoteSource.getHasNickname().asExternalModel()
        emit(result)
    }
}
