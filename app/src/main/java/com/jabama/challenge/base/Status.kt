package com.jabama.challenge.base

sealed class Status<out T> {
    data class Success<T>(val data: T) : Status<T>()
    data class Error(val message: String? = null) : Status<Nothing>()
    object Loading : Status<Nothing>()
    object NotLoaded : Status<Nothing>()

    companion object {
        fun getStatusLoading() = Loading

        fun <T : Any> getStatusSuccess(data: T) = Success(data = data)

        fun getStatusError(message: String?) = Error(message = message)

        fun getStatusNotLoaded() = NotLoaded

    }
}
