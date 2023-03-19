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

package com.ddd.pollpoll.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.ddd.pollpoll.core.data.DefaultLoginRepository
import com.ddd.pollpoll.core.database.Login
import com.ddd.pollpoll.core.database.LoginDao

/**
 * Unit tests for [DefaultLoginRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultLoginRepositoryTest {

    @Test
    fun logins_newItemSaved_itemIsReturned() {

        solution()
    }

    @Test
    fun solution(lottos: IntArray, win_nums: IntArray): IntArray {
        var answerNum = lottos.map { win_nums.contains(it) }.filter { it == true }.count().coerceAtLeast(1)

        var zero = lottos.filter { it==0 }.count().coerceAtMost(5)


        return intArrayOf(7 - (answerNum + zero), 7 - answerNum)
    }
}


private class FakeLoginDao : LoginDao {

    private val data = mutableListOf<Login>()

    override fun getLogins(): Flow<List<Login>> = flow {
        emit(data)
    }

    override suspend fun insertLogin(item: Login) {
        data.add(0, item)
    }
}
