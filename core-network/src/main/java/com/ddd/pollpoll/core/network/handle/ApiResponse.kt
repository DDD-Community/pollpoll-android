package com.ddd.pollpoll.core.network.handle

import com.ddd.pollpoll.core.exception.ResponseNullException
import retrofit2.HttpException
import retrofit2.Response

data class ApiResponse<T>(
    val data: T
)

suspend fun <T> Response<ApiResponse<T>>.executeHandle(): T {
    return try {
        val response = this
        val body = response.body()?.data
        if (response.isSuccessful && body != null) body else throw ResponseNullException(response.message())
    } catch (e: HttpException) {
        throw Exception()
    }
}

// sealed interface NetworkResult<T>
//
// class ApiSuccess<T>(val data: T) : NetworkResult<T>
// class ApiError<T>(val code: Int, val message: String?) : NetworkResult<T>
// class ApiException<T>(val e: Throwable) : NetworkResult<T>
//
// suspend fun <T : Any> handleApi(
//    execute: suspend () -> Response<T>
// ): NetworkResult<T> {
//    return try {
//        val response = execute()
//        val body = response.body()
//        if (response.isSuccessful && body != null) {
//            ApiSuccess(body)
//        } else {
//            ApiError(code = response.code(), message = response.message())
//        }
//    } catch (e: HttpException) {
//        ApiError(code = e.code(), message = e.message())
//    } catch (e: Throwable) {
//        ApiException(e)
//    }
// }
