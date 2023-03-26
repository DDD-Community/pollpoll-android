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

package com.ddd.pollpoll.feature.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.Nickname
import com.ddd.pollpoll.core.data.NickNameRepository
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val nickNameRepository: NickNameRepository,
) : ViewModel() {

    private val _recommendNicknameUiState: MutableStateFlow<RecommendNicknameUiState> =
        MutableStateFlow(RecommendNicknameUiState.Empty)
    val recommendNicknameUiState = _recommendNicknameUiState.asStateFlow()

    private val _insertNicknameUiState: MutableStateFlow<InsertNicknameUiState> =
        MutableStateFlow(InsertNicknameUiState.Empty)
    val insertNicknameUiState = _insertNicknameUiState.asStateFlow()

    init {
        recommendNickname()
    }

    fun recommendNickname() = viewModelScope.launch {
        nickNameRepository.getNickNameRecommend().asResult().collect {
            _recommendNicknameUiState.value = when (it) {
                is Result.Success -> RecommendNicknameUiState.Success(it.data?.nickname ?: "")
                is Result.Error -> RecommendNicknameUiState.Error(
                    it.exception ?: Exception("알수없는 오류"),
                )

                Result.Loading -> RecommendNicknameUiState.Loading
            }
        }
    }

    fun insertNickName(nickName: String) = viewModelScope.launch {
        nickNameRepository.putNickname(Nickname(nickName)).asResult().collect {
            _insertNicknameUiState.value = when (it) {
                is Result.Success -> InsertNicknameUiState.Success
                is Result.Error -> InsertNicknameUiState.Error(
                    it.exception ?: Exception("알수없는 오류"),
                )

                Result.Loading -> InsertNicknameUiState.Loading
            }
        }
    }
}


sealed interface RecommendNicknameUiState {

    object Empty : RecommendNicknameUiState
    data class Error(val throwable: Throwable) : RecommendNicknameUiState
    data class Success(val data: String) : RecommendNicknameUiState
    object Loading : RecommendNicknameUiState
}

sealed interface InsertNicknameUiState {

    object Empty : InsertNicknameUiState
    data class Error(val throwable: Throwable) : InsertNicknameUiState
    object Success : InsertNicknameUiState
    object Loading : InsertNicknameUiState
}
