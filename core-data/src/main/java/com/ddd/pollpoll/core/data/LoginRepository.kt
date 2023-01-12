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

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.ddd.pollpoll.core.database.Login
import com.ddd.pollpoll.core.database.LoginDao
import com.pollpoll.core_network.remote.LoginRemoteSource
import com.pollpoll.core_network.retrofit.PollAPI
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LoginRepository {
    suspend fun loginGoogle(token: String): Flow<String>
}

class LoginRepositoryImp @Inject constructor(
    private val loginRemoteSource: LoginRemoteSource
) : LoginRepository {

    override suspend fun loginGoogle(token: String): Flow<String> = flow {
        emit(loginRemoteSource.loginGoogle(token))
    }

}
