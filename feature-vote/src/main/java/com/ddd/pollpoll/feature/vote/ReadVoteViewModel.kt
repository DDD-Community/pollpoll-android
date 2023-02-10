package com.ddd.pollpoll.feature.vote

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.core.network.model.GetPostResponse
import com.ddd.pollpoll.core.network.model.PollItem
import com.ddd.pollpoll.core.network.model.PostItem
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadVoteViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
//    val posts = MutableStateFlow<GetPostResponse?>(null)
    val lastPost = MutableStateFlow<PostResponse?>(null)
//    val lastPostItems = MutableStateFlow<List<PostItem>?>(null)

    init {
        viewModelScope.launch {
            postRepository.getPost(33).asResult().collect { result ->
                when (result) {
                    is Result.Error -> Log.e("ReadVoteViewModel", "post Error ${result.exception}")
                    Result.Loading -> Log.e("ReadVoteViewModel", "post Loading")
                    is Result.Success ->  {
                        Log.e("ReadVoteViewModel", "post Success ${result.data}")
                        lastPost.value = result.data
                    }
                }
            }
        }
    }

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
