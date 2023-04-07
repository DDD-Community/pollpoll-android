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

import com.ddd.pollpoll.Category
import com.ddd.pollpoll.core.data.model.asExternalModel
import com.ddd.pollpoll.core.network.remote.CategoryRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface CategoryRepository {
    suspend fun getCategories(): Flow<List<Category>>
}

class CategoryRepositoryImp @Inject constructor(
    private val categoryRemoteSource: CategoryRemoteSource
) : CategoryRepository {
    override suspend fun getCategories(): Flow<List<Category>> = flow {
        val result = categoryRemoteSource.getCategories().asExternalModel()
        emit(result)
    }
}
