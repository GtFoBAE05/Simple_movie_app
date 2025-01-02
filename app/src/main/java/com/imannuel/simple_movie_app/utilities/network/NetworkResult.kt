package com.imannuel.simple_movie_app.utilities.network

sealed class NetworkResult<out R> private constructor() {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val msg: String) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}