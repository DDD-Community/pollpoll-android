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
import com.ddd.pollpoll.core.data.CategoryRepository
import com.ddd.pollpoll.core.data.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.ddd.pollpoll.core.network.model.Category
import kotlinx.coroutines.delay
import result.Result
import result.asResult

import javax.inject.Inject

@HiltViewModel
class MypollpollViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            categoryRepository.getCategories().asResult().collect {result ->
                when (result) {
                    is Result.Error -> Log.e("MypollpollViewModel", "category Error ${result.exception}")
                    Result.Loading -> Log.e("MypollpollViewModel", "category Loading")
                    is Result.Success ->  {
                        Log.e("MypollpollViewModel", "category Success ${result.data.categories}")
                    }
                }
            }
        }

        viewModelScope.launch {
            postRepository.getPosts(2).asResult().collect { result ->
                when (result) {
                    is Result.Error -> Log.e("MypollpollViewModel", "post Error ${result.exception}")
                    Result.Loading -> Log.e("MypollpollViewModel", "post Loading")
                    is Result.Success ->  {
                        Log.e("MypollpollViewModel", "post Success ${result.data.posts}")
                    }
                }
            }
        }

    }

}
