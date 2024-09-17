package com.jabama.challenge.domain.repository

import com.jabama.challenge.domain.model.auth.AccessToken
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepository {
    suspend fun fetchNewAccessToken(code: String): AccessToken
    fun getAccessToken(): String?
    fun getIsAuthenticatedFlow(): StateFlow<Boolean>
    fun updateIsAuthenticated(value: Boolean)
}