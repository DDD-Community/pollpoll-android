package com.ddd.pollpoll.feature.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.PollItem
import com.ddd.pollpoll.Vote
import com.ddd.pollpoll.core.data.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import result.asResult
import javax.inject.Inject

@HiltViewModel
class InsertVoteViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<InsertVoteUiState> =
        MutableStateFlow(InsertVoteUiState.SelectCategory)
    val uiState: StateFlow<InsertVoteUiState> = _uiState

    private val _vote: MutableStateFlow<Vote> = MutableStateFlow(Vote())
    val vote = _vote.asStateFlow()

    fun selectCategory(categoryId: Category) {
        _vote.update { it.copy(category = categoryId.categoryId) }
        _uiState.value = InsertVoteUiState.InsertTitle
    }

    fun changeTitle(title: String) = _vote.update {
        it.copy(title = title)
    }

    fun changeContent(content: String) = _vote.update {
        it.copy(contents = content)
    }

    fun navigateAddVoteCategory() {
        _uiState.value = InsertVoteUiState.AddVoteCategory
    }

    fun insertPost() = viewModelScope.launch {
        postRepository.insertPost(vote.value).asResult().collect {
        }
    }

    fun addVoteList() {
        val pollList = _vote.value.pollItems.plus(PollItem(""))
        _vote.update { it.copy(pollItems = pollList) }
    }

    fun changeVoteList(index: Int, pollItem: PollItem) {
        val pollList = _vote.value.pollItems.toMutableList().apply { set(index, pollItem) }
        _vote.update { it.copy(pollItems = pollList) }
    }
}

sealed interface InsertVoteUiState {

    object SelectCategory : InsertVoteUiState

    object InsertTitle : InsertVoteUiState

    object AddVoteCategory : InsertVoteUiState
}
