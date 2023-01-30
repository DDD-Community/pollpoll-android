package com.ddd.pollpoll.core.network.handle

data class ApiResponse<T>(
    val data: T
)

fun <T> ApiResponse<T>.executeHandle(): T = this.data
