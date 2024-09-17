package com.jabama.challenge.domain.usecase.auth

import com.jabama.challenge.domain.repository.AuthenticationRepository

class UpdateIsAuthenticatedUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(value: Boolean) {
        authenticationRepository.updateIsAuthenticated(value = value)
    }
}