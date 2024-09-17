package com.jabama.challenge.data.cache.auth

interface AccessTokenAuthenticationCache {
    fun getAccessToken(): String?
}