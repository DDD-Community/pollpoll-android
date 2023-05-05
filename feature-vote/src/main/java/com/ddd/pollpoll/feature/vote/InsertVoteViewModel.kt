package com.ddd.pollpoll.feature.vote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.CategoryEnum
import com.ddd.pollpoll.PollItem
import com.ddd.pollpoll.Vote
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// todo release후 대대적인 리펙토링
@HiltViewModel
class InsertVoteViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<InsertVoteUiState> =
        MutableStateFlow(InsertVoteUiState())
    val uiState: StateFlow<InsertVoteUiState> = _uiState

    fun selectCategory(categoryId: Category) {
        _uiState.update { it.copy(category = categoryId.categoryEnum) }
    }

    fun changeTitle(title: String) = _uiState.update {
        it.copy(title = title)
    }

    fun changeContent(content: String) = _uiState.update {
        it.copy(contents = content)
    }

    fun changeDate(date: Long) = _uiState.update {
        it.copy(milliseconds = date)
    }

    fun insertPost() = viewModelScope.launch {
        val vote = Vote(
            category = _uiState.value.category,
            contents = _uiState.value.contents,
            milliseconds = _uiState.value.milliseconds,
            multipleChoice = false,
            pollItems = _uiState.value.pollItems,
            title = _uiState.value.title,

        )
        postRepository.insertPost(vote).asResult().collect { result ->
            when (result) {
                Result.Loading -> Log.d("TEST", "insertPost:  로딩")
                is Result.Success -> _uiState.update { it.copy(isInsertSuccess = true) }
                is Result.Error -> Log.d("TEST", "insertPost:  실패${result.exception}")
            }
        }
    }

    fun addVoteList() {
        val pollList = _uiState.value.pollItems.plus(PollItem(""))
        _uiState.update { it.copy(pollItems = pollList.toImmutableList()) }
    }

    fun changeVoteList(index: Int, pollItem: PollItem) {
        val pollList = _uiState.value.pollItems.toMutableList().apply { set(index, pollItem) }
        _uiState.update { it.copy(pollItems = pollList.toImmutableList()) }
    }
}

data class InsertVoteUiState(
    val category: CategoryEnum = CategoryEnum.Buy,
    val title: String = "",
    val contents: String = "",
    val milliseconds: Long = 0,
    val multipleChoice: Boolean = false,
    val pollItems: ImmutableList<PollItem> = persistentListOf(PollItem(""), PollItem("")),
    val isInsertSuccess: Boolean = false,
) {
    val isVoteNotEmpty: Boolean
        get() = title.isBlank() &&
            contents.isNotBlank() &&
            !isPollItemsNotEmpty

    val isPollItemsNotEmpty: Boolean
        get() = !pollItems.contains(PollItem(""))
}

sealed interface Insert


// todo ViewState로 빼는게 맞을것 같음
