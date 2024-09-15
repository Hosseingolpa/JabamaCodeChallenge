package com.jabama.challenge.domain.repository

import com.jabama.challenge.domain.model.auth.AccessToken

interface AuthenticationRepository {
    suspend fun fetchNewAccessToken(code: String): AccessToken
    fun getAccessToken(): String?
}