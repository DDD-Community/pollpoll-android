package com.ddd.pollpoll.feature.vote

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.pollpoll.core.data.MyPageRepository
import com.ddd.pollpoll.core.data.PostRepository
import com.ddd.pollpoll.core.network.model.PostItem
import com.ddd.pollpoll.core.network.model.PostResponse
import com.ddd.pollpoll.core.network.model.PutVoteRequest
import com.ddd.pollpoll.core.result.Result
import com.ddd.pollpoll.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadVoteViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val myPageRepository: MyPageRepository
) : ViewModel() {
//    val posts = MutableStateFlow<GetPostResponse?>(null)
    val lastPost = MutableStateFlow<PostResponse?>(null)
    val beforeVote = MutableStateFlow<List<Vote>>(emptyList())
    val afterVote = MutableStateFlow<List<Vote>>(emptyList())
    val selectedIndex = MutableStateFlow<Set<Int>>(emptySet())
    val selectedPostItemIds = MutableStateFlow<List<Int>>(emptyList())


    fun selectIndex(index: Int) {
        val tempSet = selectedIndex.value.toMutableSet()
        val tempBeforeVoteList = beforeVote.value.toMutableList()

        if (selectedIndex.value.contains(index)) {
            tempSet.remove(index)
            tempBeforeVoteList[index] = tempBeforeVoteList[index].copy(isSelected = false)
        } else {
            tempSet.add(index)
            tempBeforeVoteList[index] = tempBeforeVoteList[index].copy(isSelected = true)
        }
        selectedIndex.value = tempSet.toSet()
        beforeVote.value = tempBeforeVoteList.toList()
    }

    suspend fun setPostId(postId: Int) {
        postRepository.getPost(postId).asResult().collect { result ->
            when (result) {
                is Result.Error -> Log.e("ReadVoteViewModel", "post Error ${result.exception}")
                Result.Loading -> Log.e("ReadVoteViewModel", "post Loading")
                is Result.Success -> {
                    Log.e("ReadVoteViewModel", "post Success ${result.data}")
                    lastPost.value = result.data
                    val votes = lastPost.value?.pollItems
                    votes?.let {
                        val tempList = mutableListOf<Vote>()
                        for ((index, postItem) in it.withIndex()) {
                            val item = Vote(
                                index = index,
                                text = postItem.name,
                                percent = 0f,
                                voteCount = postItem.count,
                                isSelected = false,
                                onClick = {},
                                postItemId = postItem.pollItemId
                            )
                            tempList.add(item)
                        }
                        beforeVote.value = tempList
                    }

                }
            }
        }
    }

    var voted = mutableStateOf(false)

    fun reVote() {
        voted.value = false
        selectedIndex.value = emptySet()

        val votes = lastPost.value?.pollItems
        votes?.let {
            val tempList = mutableListOf<Vote>()
            for ((index, postItem) in it.withIndex()) {
                val item = Vote(
                    index = index,
                    text = postItem.name,
                    percent = 0f,
                    voteCount = postItem.count,
                    isSelected = false,
                    onClick = {},
                    postItemId = postItem.pollItemId
                )
                tempList.add(item)
            }
            beforeVote.value = tempList
        }
    }


    fun vote(votedIndex: List<Int>) {
        lastPost.value?.pollItems?.let {
            val tempPostItemList = setAfterVote(it, votedIndex)
//            selectedPostItemIds.value = tempPostItemList.toList()

            viewModelScope.launch {
                postRepository.putPoll(lastPost.value!!.pollId, PutVoteRequest(tempPostItemList)).asResult().collect {
                    when (it) {
                        Result.Loading -> Log.d("TEST", "putPoll:  로딩")
                        is Result.Success -> Log.d("TEST", "putPoll:  success")
                        is Result.Error -> Log.d("TEST", "putPoll:  실패${it.exception}")
                    }
                }
            }
        }
    }

    private fun setAfterVote(it: List<PostItem>, votedIndex: List<Int>):MutableList<Int> {
        var voteParticipantCount = votedIndex.size // 투표한 수 만큼 시작
        for (item in it) {
            voteParticipantCount += item.count
        }
        val tempList = mutableListOf<Vote>()
        val tempPostItemList = mutableListOf<Int>()
        for ((i, item) in it.withIndex()) {
            val voteCountAfterVote = if (votedIndex.contains(i)) {
                tempPostItemList.add(item.pollItemId)
                item.count + 1
            } else {
                item.count
            }
            tempList.add(
                Vote(
                    index = i,
                    text = item.name,
                    percent = voteCountAfterVote.toFloat() / voteParticipantCount.toFloat(),
                    voteCount = voteCountAfterVote,
                    isSelected = false,
                    onClick = {},
                    item.pollItemId
                )
            )
        }
        afterVote.value = tempList
        voted.value = true
        return tempPostItemList
    }

    private suspend fun selectMyPollType(type: String) {
        myPageRepository.getMyPageType(type).asResult().collect { result ->
            when (result) {
                is Result.Error -> Log.e(
                    "ReadVoteViewModel",
                    "getMyPageType Error ${result.exception}, Type : $type"
                )

                Result.Loading -> Log.e("ReadVoteViewModel", "getMyPageType Loading, Type : $type")
                is Result.Success -> {
                    Log.e("ReadVoteViewModel", "getMyPageType Success ${result.data}, Type : $type")
                    result.data?.let { myPageType ->
                        val tempList = emptyList<Int>().toMutableList()
                        for (post in myPageType.posts) {
                            tempList.add(post.postId)
                        }
                    }
                }
            }
        }
    }
}

data class Vote(
    val index: Int,
    val text: String,
    val percent: Float,
    val voteCount: Int,
    val isSelected: Boolean,
    val onClick: () -> Unit,
    val postItemId: Int
)
