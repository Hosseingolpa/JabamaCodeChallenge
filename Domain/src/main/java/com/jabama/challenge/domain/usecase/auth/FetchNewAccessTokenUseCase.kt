package com.jabama.challenge.domain.usecase.auth

import com.jabama.challenge.domain.model.auth.AccessToken
import com.jabama.challenge.domain.repository.AuthenticationRepository

class FetchNewAccessTokenUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun execute(code: String): AccessToken {
        return authenticationRepository.fetchNewAccessToken(code = code)
    }
}