package com.ddd.pollpoll

data class Vote(
    val title: String = "",
    val content: String = "",
    val voteItem: List<String> = listOf(),
    val isMultiple: Boolean = false,
    val voteTime: Int = 0
)
