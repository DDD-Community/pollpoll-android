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
import com.ddd.pollpoll.core.data.LoginRepository
import com.ddd.pollpoll.core.data.NickNameRepository
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val nickNameRepository: NickNameRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun addLogin(token: String) {
        viewModelScope.launch {
            loginRepository.loginGoogle(token).asResult().collect { result ->
                when (result) {
                    is Result.Error -> _uiState.update {
                        LoginUiState.Error(result.exception ?: Exception("알수없는 오류"))
                    }

                    Result.Loading -> _uiState.update { LoginUiState.Loading }

                    is Result.Success -> {
                        verifyHasNickName()
                    }
                }
            }
        }
    }

    private fun verifyHasNickName() = viewModelScope.launch {
        nickNameRepository.getHasNickname().asResult().collect { result ->
            when (result) {
                is Result.Error -> _uiState.update {
                    LoginUiState.Error(result.exception ?: Exception("알수없는 오류"))
                }

                Result.Loading -> _uiState.update { LoginUiState.Loading }

                is Result.Success -> {
                    _uiState.update { if (result.data?.hasNickname != true) LoginUiState.HasNickName else LoginUiState.NotNickName }
                }
            }
        }
    }
}

sealed interface LoginUiState {

    object Empty : LoginUiState
    data class Error(val throwable: Throwable) : LoginUiState
    object HasNickName : LoginUiState
    object NotNickName : LoginUiState
    object Loading : LoginUiState
}
