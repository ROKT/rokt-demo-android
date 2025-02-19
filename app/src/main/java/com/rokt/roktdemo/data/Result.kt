package com.rokt.roktdemo.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.data(): T {
    return (this as Result.Success<T>).data
}

fun <T> Result<T>.exception(): Exception {
    return (this as Result.Error).exception
}
