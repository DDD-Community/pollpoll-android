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

package com.ddd.pollpoll.feature.mypollpoll.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.data.CategoryRepository
import com.ddd.pollpoll.core.data.MyPageRepository
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.core.network.model.GetPostResponse
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MyPollPollUiState(
    val myPollSelected: Boolean,
    val myPollCount: Int,
    val participatePollSelected: Boolean,
    val participateCount: Int,
    val watchPollSelected: Boolean,
    val watchPollCount: Int,

)

@HiltViewModel
class MypollpollViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val postRepository: PostRepository,
    private val myPageRepository: MyPageRepository
) : ViewModel() {
    val posts = MutableStateFlow<List<Post>>(emptyList())
    val uiState = MutableStateFlow(MyPollPollUiState(true, 0, false, 0, false, 0))

    fun myPollClicked() {
        uiState.value = uiState.value.copy(
            myPollSelected = true,
            participatePollSelected = false,
            watchPollSelected = false
        )
    }
    fun participatePollClicked() {
        uiState.value = uiState.value.copy(
            myPollSelected = false,
            participatePollSelected = true,
            watchPollSelected = false
        )
    }
    fun watchPollClicked() {
        uiState.value = uiState.value.copy(
            myPollSelected = false,
            participatePollSelected = false,
            watchPollSelected = true
        )
    }

    init {
        viewModelScope.launch {
            myPageRepository.getMyPageType("MY_POLL").asResult().collect { result ->
                when (result) {
                    is Result.Error -> Log.e(
                        "MypollpollViewModel",
                        "getMyPageType Error ${result.exception}"
                    )

                    Result.Loading -> Log.e("MypollpollViewModel", "getMyPageType Loading")
                    is Result.Success -> {
                        Log.e("MypollpollViewModel", "getMyPageType Success ${result.data}")
                        result.data?.let { myPageType ->
                            Log.e("MypollpollViewModel", "myPageType : $myPageType")
                            uiState.value = uiState.value.copy(
                                myPollCount = myPageType.myPollCount,
                                participateCount = myPageType.participatePollCount,
                                watchPollCount = myPageType.watchPollCount
                            )
                            posts.value = myPageType.posts
                        }
                    }
                }
            }
        }

    }
}
