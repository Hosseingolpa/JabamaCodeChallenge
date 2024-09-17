package com.jabama.challenge.domain.usecase

import com.jabama.challenge.domain.repository.AuthenticationRepository
import com.jabama.challenge.domain.repository.SearchRepository
import com.jabama.challenge.domain.usecase.search.GetRepositoriesUseCase

class IsUserAuthenticatedByValidationUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
) {
    suspend fun execute(): Boolean {
        val accessToken = authenticationRepository.getAccessToken()
        val isTokenValid = accessToken?.let { isTokenValid() }?: false
        authenticationRepository.updateIsAuthenticated(value = isTokenValid)
        return isTokenValid
    }

    private suspend fun isTokenValid(): Boolean {
        try {
            getRepositoriesUseCase.execute(query = "a")
            return true
        } catch (e: Exception) {
            return false
        }
    }
}