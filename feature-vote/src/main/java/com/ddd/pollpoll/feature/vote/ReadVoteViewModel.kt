package com.ddd.pollpoll.feature.vote

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadVoteViewModel @Inject constructor() : ViewModel() {
    var votes = mutableStateListOf<Vote>(
        Vote(0, "8K", 0f, 0, false) { toggleSelected(0) },
        Vote(1, "4K", 0.1f, 100, false) { toggleSelected(1) },
        Vote(2, "QHD", 0.05f, 50, false) { toggleSelected(2) },
        Vote(3, "FHD", 0.85f, 850, false) { toggleSelected(3) },
    )

    var voted = mutableStateOf(false)

    fun reVote() {
        voted.value = false
    }

    fun voteSelectedVote() {
        voted.value = true
    }

    fun toggleSelected(index: Int) {
        votes[index] = votes[index].copy ( isSelected =  !votes[index].isSelected)
    }
}
