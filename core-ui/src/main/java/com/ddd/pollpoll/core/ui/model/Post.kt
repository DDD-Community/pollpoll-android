package com.ddd.pollpoll.feature.main.model

import androidx.compose.runtime.Stable
import com.ddd.pollpoll.Post
import com.ddd.pollpoll.PostItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
data class PostUi(
    val categoryName: String,
    val contents: String,
    val nickname: String,
    val participantCount: Int,
    val pollEndAt: Long,
    val pollId: Int,
    val pollItemCount: Int,
    val postCreatedAt: Long,
    val postHits: Int,
    val postId: Int,
    val pollItems: ImmutableList<PostItemUi>?,
    val title: String,
    val watcherCount: Int,
)

@Stable
data class PostItemUi(
    val isPolled: Boolean,
    val pollItemId: Int,
    val name: String,
    val count: Int,
)

fun PostItem.toUiModel() = PostItemUi(
    isPolled = isPolled,
    pollItemId = pollItemId,
    name = name,
    count = count,
)

fun Post.toUiModel() = PostUi(
    categoryName = categoryName,
    contents = contents,
    nickname = nickname,
    participantCount = participantCount,
    pollEndAt = pollEndAt,
    pollId = pollId,
    pollItemCount = pollItemCount,
    postCreatedAt = postCreatedAt,
    postHits = postHits,
    postId = postId,
    pollItems = pollItems?.map { it.toUiModel() }?.toImmutableList(),
    title = title,
    watcherCount = watcherCount,
)
