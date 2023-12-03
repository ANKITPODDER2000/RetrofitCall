package com.example.retrofitcall.api

sealed class ApiResponse {
    object NotStarted : ApiResponse()
    object Started : ApiResponse()
    class Success<T>(val response: T) : ApiResponse()
    class Error(val errorCode: String): ApiResponse()
}