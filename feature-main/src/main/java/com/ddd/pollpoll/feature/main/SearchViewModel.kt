package com.ddd.pollpoll.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.feature.main.model.PostUi
import com.ddd.pollpoll.feature.main.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    val posts = mutableStateListOf<PostUi>()

    private var lastPostId: Int? by mutableStateOf(null)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.NONE)

    // 검색하면, lastPostId가 초기화 되어야함.
    fun searchPost(searchText: String) {
        lastPostId = null
        canPaginate = false
        listState = ListState.IDLE
        posts.clear()
        getPost(searchText)
    }

    fun getPost(searchText: String) = viewModelScope.launch {
        if (lastPostId == null || (lastPostId != 1 && canPaginate) && listState == ListState.IDLE) {
            listState = if (lastPostId == null) ListState.LOADING else ListState.PAGINATING
            postRepository.getPosts(lastPostId, searchText).catch {
                when (it) {
                    is NullPointerException -> {
                        listState =
                            if (lastPostId == null) ListState.EMPTY else ListState.PAGINATION_EXHAUST
                    }

                    else -> {
                        listState = ListState.ERROR
                    }
                }
            }.collect { post ->
                canPaginate = post.last().postId != 1
                posts.addAll(post.map(Post::toUiModel))
                listState = ListState.IDLE
                if (canPaginate) lastPostId = post.last().postId
            }
        }
    }
}
