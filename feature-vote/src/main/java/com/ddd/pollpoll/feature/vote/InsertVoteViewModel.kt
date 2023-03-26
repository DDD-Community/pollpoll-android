package com.ddd.pollpoll.feature.vote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.PollItem
import com.ddd.pollpoll.Vote
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// todo release후 대대적인 리펙토링
@HiltViewModel
class InsertVoteViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<InsertVoteUiState> =
        MutableStateFlow(InsertVoteUiState.SelectCategory)
    val uiState: StateFlow<InsertVoteUiState> = _uiState

    private val _vote: MutableStateFlow<Vote> = MutableStateFlow(Vote(milliseconds = 86400000))
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

    fun changeDate(date: Long) = _vote.update {
        it.copy(milliseconds = date)
    }

    fun navigateAddVoteCategory() {
        _uiState.value = InsertVoteUiState.AddVoteCategory
    }

    fun backAddVote() {
        _uiState.value = when (_uiState.value) {
            InsertVoteUiState.AddVoteCategory -> InsertVoteUiState.InsertTitle
            InsertVoteUiState.InsertTitle -> InsertVoteUiState.SelectCategory
            InsertVoteUiState.SelectCategory -> InsertVoteUiState.Back
            InsertVoteUiState.Back -> InsertVoteUiState.Back
            is InsertVoteUiState.Success -> InsertVoteUiState.Back
        }
    }

    fun insertPost() = viewModelScope.launch {
        postRepository.insertPost(vote.value).asResult().collect {
            when (it) {
                Result.Loading -> Log.d("TEST", "insertPost:  로딩")
                is Result.Success -> _uiState.update { InsertVoteUiState.Success("") }
                is Result.Error -> Log.d("TEST", "insertPost:  실패${it.exception}")
            }
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

data class InsertVoteUiStateTest(
    val vote: Vote = Vote(),

) {
    val isInsertButtonEnabled: Boolean
        get() = vote.title.isBlank() &&
            vote.contents.isNotBlank() &&
            !vote.pollItems.contains(PollItem(""))

    val isCompleteButtonEnabled: Boolean
        get() = !vote.pollItems.contains(PollItem(""))
}

sealed interface InsertVoteUiState {

    object SelectCategory : InsertVoteUiState

    object InsertTitle : InsertVoteUiState

    object AddVoteCategory : InsertVoteUiState

    data class Success(val text: String) : InsertVoteUiState

    object Back : InsertVoteUiState
}
