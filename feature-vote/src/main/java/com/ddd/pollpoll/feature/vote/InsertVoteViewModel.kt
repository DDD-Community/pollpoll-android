package com.ddd.pollpoll.feature.vote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

    private val _textField: MutableStateFlow<FormData> = MutableStateFlow(FormData())

    val textField = _textField.asStateFlow()

//    fun addTitle(title: String) {
//        _uiState.title = title
//    }
//
//    fun addContent(content: String) {
//        _uiState.content = content
//    }

    fun selectCategory(category: Category) = _textField.update {
        it.copy(category = category)
    }

    fun insertTitle(title: String) = _textField.update {
        it.copy(title = title)
    }

    fun insertContent(content: String) = _textField.update {
        it.copy(content = content)
    }
}

data class FormData(
    val category: Category = Category(iconDrawable = PollIcon.Buy, "구매"),
    val title: String = "",
    val content: String = ""
)

// 어떤 화면을 표출할것인가?
sealed interface InsertVoteUiState {
//    object Loading : InterestsUiState
//
//    data class Interests(
//        val topics: List<FollowableTopic>
//    ) : InterestsUiState

    object SelectCategory : InsertVoteUiState

    data class InsertTitle(val category: Category, val title: String) : InsertVoteUiState
}
