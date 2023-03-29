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
        _uiState.update {
            it.copy(category = categoryId.categoryEnum, insertVoteStep = InsertVoteStep.InsertTitle)
        }
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

    fun navigateAddVoteCategory() {
        _uiState.update { it.copy(insertVoteStep = InsertVoteStep.AddVoteCategory) }
    }

    fun backAddVote() {
        val insertVoteStep = when (_uiState.value.insertVoteStep) {
            InsertVoteStep.AddVoteCategory -> InsertVoteStep.InsertTitle
            InsertVoteStep.InsertTitle -> InsertVoteStep.SelectCategory
            InsertVoteStep.SelectCategory -> InsertVoteStep.SelectCategory
        }
        _uiState.update {
            it.copy(insertVoteStep = insertVoteStep)
        }
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
        postRepository.insertPost(vote).asResult().collect {
            when (it) {
                Result.Loading -> Log.d("TEST", "insertPost:  로딩")
                is Result.Success -> Log.d("TEST", "insertPost:  success")
                is Result.Error -> Log.d("TEST", "insertPost:  실패${it.exception}")
            }
        }
    }

    fun addVoteList() {
        val pollList = _uiState.value.pollItems.plus(PollItem(""))
        _uiState.update { it.copy(pollItems = pollList) }
    }

    fun changeVoteList(index: Int, pollItem: PollItem) {
        val pollList = _uiState.value.pollItems.toMutableList().apply { set(index, pollItem) }
        _uiState.update { it.copy(pollItems = pollList) }
    }
}

data class InsertVoteUiState(
    val category: CategoryEnum = CategoryEnum.Buy,
    val title: String = "",
    val contents: String = "",
    val milliseconds: Long = 0,
    val multipleChoice: Boolean = false,
    val pollItems: List<PollItem> = listOf(PollItem(""), PollItem("")),
    val insertVoteStep: InsertVoteStep = InsertVoteStep.SelectCategory,
) {
    val isVoteNotEmpty: Boolean
        get() = title.isBlank() &&
            contents.isNotBlank() &&
            !isPollItemsNotEmpty

    val isPollItemsNotEmpty: Boolean
        get() = !pollItems.contains(PollItem(""))
}

sealed interface InsertVoteStep {

    object SelectCategory : InsertVoteStep
    object InsertTitle : InsertVoteStep
    object AddVoteCategory : InsertVoteStep
}
