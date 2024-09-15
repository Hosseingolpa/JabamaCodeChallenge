package com.jabama.challenge.domain.usecase

import com.jabama.challenge.domain.repository.AuthenticationRepository

class GetAccessTokenUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(): String? {
        return authenticationRepository.getAccessToken()
    }
}