package com.ddd.pollpoll

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InsertVoteViewModel @Inject constructor() : ViewModel() {

    private var _voteStateFlow: MutableStateFlow<Vote> = MutableStateFlow(Vote())
    val voteStateFlow: StateFlow<Vote> = _voteStateFlow.asStateFlow()
}

sealed interface InsertUiState {
    object Loading : InsertUiState
    data class Error(val throwable: Throwable) : InsertUiState
    data class Success(val data: List<String>) : InsertUiState
}
