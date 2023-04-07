package com.ddd.pollpoll.ui.feature

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.core.data.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    val posts = mutableStateListOf<Post>()
    private var lastPostId: Int? by mutableStateOf(null)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.NONE)

    // Resets lastPostId when a search is performed
    fun searchPost(searchText: String) {
        resetLastPostId()
        clearPosts()
        updateListState(ListState.IDLE)
        getPost(searchText)
    }

    fun getPost(searchText: String) = viewModelScope.launch {
        if (shouldFetchPosts()) {
            updateListState(loadingState())
            fetchPosts(searchText)
        }
    }

    private fun shouldFetchPosts() =
        lastPostId == null || (lastPostId != 1 && canPaginate) && listState == ListState.IDLE

    private fun loadingState() = if (lastPostId == null) ListState.LOADING else ListState.PAGINATING

    private fun fetchPosts(searchText: String) {
        viewModelScope.launch {
            postRepository.getPosts(lastPostId, searchText)
                .catch { handleException(it) }
                .collect { newPosts ->
                    updatePosts(newPosts)
                }
        }
    }

    private fun handleException(exception: Throwable) {
        when (exception) {
            is NullPointerException -> {
                updateListState(
                    if (lastPostId == null) ListState.EMPTY else ListState.PAGINATION_EXHAUST,
                )
            }

            else -> {
                updateListState(ListState.ERROR)
            }
        }
    }

    private fun updatePosts(newPosts: List<Post>) {
        canPaginate = newPosts.last().postId != 1
        posts.addAll(newPosts)
        updateListState(ListState.IDLE)
        if (canPaginate) lastPostId = newPosts.last().postId
    }

    private fun resetLastPostId() {
        lastPostId = null
        canPaginate = false
    }

    private fun clearPosts() {
        posts.clear()
    }

    private fun updateListState(newState: ListState) {
        listState = newState
    }
}
