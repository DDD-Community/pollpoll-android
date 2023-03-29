package com.ddd.pollpoll.ui.feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.PopularPost
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.data.CategoryRepository
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.core.network.model.GetPostResponse
import com.ddd.pollpoll.core.network.model.asExternalModel
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import com.ddd.pollpoll.feature.vote.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// todo release후 대대적인 리펙토링
@HiltViewModel
class MainViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    private val _categoryUiState: MutableStateFlow<CategoryUiState> =
        MutableStateFlow(CategoryUiState.Loading)
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState.asStateFlow()

    private val _popularUiState: MutableStateFlow<PopularUiState> =
        MutableStateFlow(PopularUiState.Loading)
    val popularUiState: StateFlow<PopularUiState> = _popularUiState.asStateFlow()

    val posts = MutableStateFlow<List<Post>>(emptyList())

    init {
        viewModelScope.launch {
            categoryRepository.getCategories().asResult().collect { result ->
                when (result) {
                    is Result.Error -> Log.e(
                        "MypollpollViewModel",
                        "category Error ${result.exception}"
                    )

                    Result.Loading -> Log.e("MypollpollViewModel", "category Loading")
                    is Result.Success -> {
                        _categoryUiState.update {
                            CategoryUiState.Success(result.data ?: listOf())
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            postRepository.getPopularPost().asResult().collect { result ->
                when (result) {
                    is Result.Error -> Log.e(
                        "MypollpollViewModel",
                        "category Error ${result.exception}"
                    )

                    Result.Loading -> Log.e("MypollpollViewModel", "category Loading")
                    is Result.Success -> {
                        _popularUiState.update {
                            Log.d("mainViemodel" , "${result.data}")
                            PopularUiState.Success(result.data!!)
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            postRepository.getPosts(100).asResult().collect { result ->
                when (result) {
                    is Result.Error -> Log.e(
                        "MypollpollViewModel",
                        "post Error ${result.exception}"
                    )

                    Result.Loading -> Log.e("MypollpollViewModel", "post Loading")
                    is Result.Success -> {
                        Log.e("MypollpollViewModel", "post Success ${result.data?.posts}")
                        result.data?.posts?.let {
                            posts.value = it.map { postResponse ->
                                postResponse.asExternalModel()
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed interface CategoryUiState {
    object Loading : CategoryUiState

    data class Success(val categoryList: List<com.ddd.pollpoll.Category>) : CategoryUiState
}

sealed interface PopularUiState {
    object Loading : PopularUiState

    data class Success(val categoryList: PopularPost) : PopularUiState
}
