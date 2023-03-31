package com.ddd.pollpoll.ui.feature

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.PopularPost
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.data.CategoryRepository
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _categoryUiState: MutableStateFlow<CategoryUiState> =
        MutableStateFlow(CategoryUiState.Loading)
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState.asStateFlow()

    private val _popularUiState: MutableStateFlow<PopularUiState> =
        MutableStateFlow(PopularUiState.Loading)
    val popularUiState: StateFlow<PopularUiState> = _popularUiState.asStateFlow()

    val posts = mutableStateListOf<Post>()
    private var lastPostId: Int? by mutableStateOf(null)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.IDLE)

    init {
        getPost()

        viewModelScope.launch {
            categoryRepository.getCategories().asResult().collect { result ->
                when (result) {
                    is Result.Error -> Log.e(
                        "MypollpollViewModel",
                        "category Error ${result.exception}",
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
                        "category Error ${result.exception}",
                    )

                    Result.Loading -> Log.e("MypollpollViewModel", "category Loading")
                    is Result.Success -> {
                        _popularUiState.update {
                            Log.d("mainViemodel", "${result.data}")
                            PopularUiState.Success(result.data!!)
                        }
                    }
                }
            }
        }
    }

    fun getPost() = viewModelScope.launch {
        if (lastPostId == null || (lastPostId != 1 && canPaginate) && listState == ListState.IDLE) {
            listState = if (lastPostId == null) ListState.LOADING else ListState.PAGINATING

            postRepository.getPosts(lastPostId).catch {
                listState =
                    if (lastPostId == null) ListState.ERROR else ListState.PAGINATION_EXHAUST
            }.collect {
                canPaginate = it.last().postId != 1
                posts.addAll(it)
                listState = ListState.IDLE
                if (canPaginate) lastPostId = it.last().postId
            }
        }
    }
}

enum class ListState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
}

sealed interface CategoryUiState {
    object Loading : CategoryUiState
    data class Error(val message: String) : CategoryUiState
    data class Success(val categoryList: List<com.ddd.pollpoll.Category>) : CategoryUiState
}

sealed interface PopularUiState {
    object Loading : PopularUiState
    data class Error(val message: String) : PopularUiState
    data class Success(val categoryList: PopularPost) : PopularUiState
}
