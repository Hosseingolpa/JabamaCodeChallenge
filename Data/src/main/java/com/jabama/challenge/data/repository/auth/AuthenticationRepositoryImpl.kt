package com.jabama.challenge.data.repository.auth

import com.jabama.challenge.data.cache.auth.AuthenticationCache
import com.jabama.challenge.data.model.auth.AccessTokenRequestDto
import com.jabama.challenge.data.remote.auth.ACCESS_TOKEN_STATE
import com.jabama.challenge.data.remote.auth.AuthenticationService
import com.jabama.challenge.data.remote.auth.CLIENT_ID
import com.jabama.challenge.data.remote.auth.CLIENT_SECRET
import com.jabama.challenge.data.remote.auth.REDIRECT_URI
import com.jabama.challenge.data.repository.auth.mapper.mapToAccessToken
import com.jabama.challenge.domain.model.auth.AccessToken
import com.jabama.challenge.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AuthenticationRepositoryImpl (
    private val service: AuthenticationService,
    private val cache: AuthenticationCache
): AuthenticationRepository {

    private val isAuthenticatedFlow = MutableStateFlow(false)

    override suspend fun fetchNewAccessToken(code: String): AccessToken {
        val accessTokenRequest = getAccessTokenRequestDto(code)
        val accessTokenResponse = service.fetchNewAccessToken(
            accessTokenRequest = accessTokenRequest
        )

        cache.saveAccessToken(accessTokenResponse.accessToken)
        return accessTokenResponse.mapToAccessToken()
    }

    private fun getAccessTokenRequestDto(code: String): AccessTokenRequestDto {
        return AccessTokenRequestDto(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            code = code,
            redirectUri = REDIRECT_URI,
            state = ACCESS_TOKEN_STATE
        )
    }

    override fun getAccessToken(): String? {
        return cache.getAccessToken()
    }

    override fun getIsAuthenticatedFlow(): StateFlow<Boolean> {
        return isAuthenticatedFlow
    }

    override fun updateIsAuthenticated(value: Boolean) {
        isAuthenticatedFlow.update { value }
    }
}