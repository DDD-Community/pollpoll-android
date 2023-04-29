package com.ddd.pollpoll.feature.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.Category
import com.ddd.pollpoll.PopularPost
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.data.CategoryRepository
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import com.ddd.pollpoll.feature.main.model.PostUi
import com.ddd.pollpoll.feature.main.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _mainUiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    val posts = mutableStateListOf<PostUi>()
    private var lastPostId: Int? by mutableStateOf(null)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.EMPTY)

    fun refreshPost() {
        posts.clear()
        lastPostId = null
        canPaginate = false
        listState = ListState.EMPTY
        getPost()
    }
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
                        _mainUiState.update {
                            it.copy(
                                categoryUiState = CategoryUiState.Success(
                                    result.data ?: listOf(),
                                ),
                            )
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

                    Result.Loading -> Log.e("MypollpollViewModel", "popular Loading")
                    is Result.Success -> {

                        _mainUiState.update {
                            it.copy(
                                popularUiState = PopularUiState.Success(
                                    popularPostList = result.data!!,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    fun selectCategory(categoryId: Int) = viewModelScope.launch {
        _mainUiState.update { it.copy(selectedCategory = categoryId) }
        refreshPost()
    }

    fun getPost() = viewModelScope.launch {
        if (lastPostId == null || (lastPostId != 1 && canPaginate) && listState == ListState.IDLE) {
            listState = if (lastPostId == null) ListState.LOADING else ListState.PAGINATING

            postRepository.getPosts(lastPostId, categoryId = mainUiState.value.selectedCategory)
                .catch {
                    listState =
                        if (lastPostId == null) ListState.ERROR else ListState.PAGINATION_EXHAUST
                }.collect {
                    canPaginate = it.last().postId != 1
                    posts.addAll(it.map(Post::toUiModel))
                    listState = ListState.IDLE
                    if (canPaginate) lastPostId = it.last().postId
                }
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

data class MainUiState(
    val selectedCategory: Int? = null,
    val popularUiState: PopularUiState = PopularUiState.Loading,
    val categoryUiState: CategoryUiState = CategoryUiState.Loading,
)

sealed interface CategoryUiState {
    object Loading : CategoryUiState
    data class Error(val message: String) : CategoryUiState
    data class Success(val categoryList: List<Category>) : CategoryUiState
}

sealed interface PopularUiState {
    object Loading : PopularUiState
    data class Error(val message: String) : PopularUiState
    data class Success(val popularPostList: PopularPost) : PopularUiState
}
