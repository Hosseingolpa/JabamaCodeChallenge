package com.jabama.challenge.data.cache.auth

interface AuthenticationCache {
    fun saveAccessToken(value: String)
    fun getAccessToken(): String?
}