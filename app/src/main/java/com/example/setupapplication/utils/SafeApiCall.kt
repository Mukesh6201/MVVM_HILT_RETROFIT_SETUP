package com.example.setupapplication.utils

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultState<T> {
    return try {
        ResultState.Success(apiCall())
    } catch (e: Exception) {
        ResultState.Error(e.message ?: "Unknown error")
    }
}
