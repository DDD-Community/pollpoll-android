package com.ddd.pollpoll.feature.vote

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.ddd.pollpoll.designsystem.icon.PollIcon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InsertVoteViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<InsertVoteUiState> =
        MutableStateFlow(InsertVoteUiState.SelectCategory)
    val uiState: StateFlow<InsertVoteUiState> = _uiState

    private val _textField: MutableStateFlow<AddingVote> = MutableStateFlow(AddingVote())

    val textField = _textField.asStateFlow()

//    fun addTitle(title: String) {
//        _uiState.title = title
//    }
//
//    fun addContent(content: String) {
//        _uiState.content = content
//    }

    fun selectCategory(category: Category) {
        _textField.update {
            it.copy(category = category)
        }
        _uiState.value = InsertVoteUiState.InsertTitle
    }

    fun insertTitle(title: String) = _textField.update {
        it.copy(title = title)
    }

    fun insertContent(content: String) = _textField.update {
        it.copy(content = content)
    }

    fun navigateAddVoteCategory() {
        _uiState.value = InsertVoteUiState.AddVoteCategory
    }
}

<<<<<<< Updated upstream
data class AddingVote(
    val category: Category = Category(iconDrawable = PollIcon.Buy, "구매"),
    val title: String = "",
    val content: String = "",
    var voteItemListState: SnapshotStateList<String> = mutableStateListOf<String>()
)
=======
    fun backAddVote() {
        _uiState.value = when (_uiState.value) {
            InsertVoteUiState.AddVoteCategory -> InsertVoteUiState.InsertTitle
            InsertVoteUiState.InsertTitle -> InsertVoteUiState.SelectCategory
            InsertVoteUiState.SelectCategory -> InsertVoteUiState.Back
            InsertVoteUiState.Back -> InsertVoteUiState.Back
        }
    }

    fun insertPost() = viewModelScope.launch {
        postRepository.insertPost(vote.value).asResult().collect {
            it.toString()
        }
    }

    fun addVoteList() {
        val pollList = _vote.value.pollItems.plus(PollItem(""))
        _vote.update { it.copy(pollItems = pollList) }
    }
>>>>>>> Stashed changes


interface InsertVoteUiState {

    object SelectCategory : InsertVoteUiState

    object InsertTitle : InsertVoteUiState

    object AddVoteCategory : InsertVoteUiState
<<<<<<< Updated upstream
=======

    object Back : InsertVoteUiState

    object Success : InsertVoteUiState
    data class Error(val throwable: Throwable) : InsertVoteUiState
    object Loading : InsertVoteUiState
>>>>>>> Stashed changes
}
