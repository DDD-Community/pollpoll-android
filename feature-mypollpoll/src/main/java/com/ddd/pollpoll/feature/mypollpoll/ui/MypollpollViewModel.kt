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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.MyPagePollType
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.data.CategoryRepository
import com.ddd.pollpoll.core.data.MyPageRepository
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.feature.main.model.PostUi
import com.ddd.pollpoll.feature.main.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyPollPollUiState(
    val myPagePollType: MyPagePollType,
    val myPollCount: Int,
    val participateCount: Int,
    val watchPollCount: Int,

)

@HiltViewModel
class MypollpollViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : ViewModel() {

    val uiState =
        MutableStateFlow(MyPollPollUiState(myPagePollType = MyPagePollType.MY_POLL, 0, 0, 0))
    val posts = mutableStateListOf<PostUi>()
    private var lastPostId: Int? by mutableStateOf(null)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.EMPTY)

    init {
        getPost()
    }

    fun changePollType(myPagePollType: MyPagePollType) {
        if (uiState.value.myPagePollType == myPagePollType) return
        lastPostId = null
        listState = ListState.IDLE
        posts.clear()
        uiState.value = uiState.value.copy(myPagePollType = myPagePollType)
        getPost()
    }

    fun getPost() = viewModelScope.launch {
        if (lastPostId == null || (lastPostId != 1 && canPaginate) && listState == ListState.IDLE) {
            listState = if (lastPostId == null) ListState.LOADING else ListState.PAGINATING

            myPageRepository.getMyPageType(lastPostId, type = uiState.value.myPagePollType).catch {
                listState =
                    if (lastPostId == null) ListState.ERROR else ListState.PAGINATION_EXHAUST
            }.collect { result ->
                if (result.posts.isEmpty()) {
                    return@collect
                }
                uiState.update {
                    uiState.value.copy(
                        myPollCount = result.myPollCount,
                        participateCount = result.participatePollCount,
                        watchPollCount = result.watchPollCount,
                    )
                }
                // postId가 1이 아닐경우, 계속 페이징이 가능하다.
                canPaginate = result.posts.last().postId != 1
                posts.addAll(result.posts.map(Post::toUiModel))
                listState = ListState.IDLE
                // 현재 시점기준, 마지막 post가 기준이 된다.
                if (canPaginate) lastPostId = result.posts.last().postId
            }
        }
    }

    enum class ListState {

        NONE,

        // 빈상태
        EMPTY,

        // 대기중
        IDLE,

        // 페이지 로딩중
        LOADING,

        // 페이지중
        PAGINATING,

        // 에러남,
        ERROR,

        // 페이지 끝
        PAGINATION_EXHAUST,
    }
}
