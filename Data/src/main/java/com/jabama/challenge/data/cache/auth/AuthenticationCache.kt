package com.jabama.challenge.data.cache.auth

interface AuthenticationCache: AccessTokenAuthenticationCache {
    fun saveAccessToken(value: String)
}