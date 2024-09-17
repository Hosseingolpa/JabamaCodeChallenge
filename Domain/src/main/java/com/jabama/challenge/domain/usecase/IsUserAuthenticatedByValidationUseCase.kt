package com.jabama.challenge.domain.usecase

import com.jabama.challenge.domain.repository.AuthenticationRepository
import com.jabama.challenge.domain.repository.SearchRepository

class IsUserAuthenticatedByValidationUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val searchRepository: SearchRepository,
) {
    suspend fun execute(): Boolean {
        val accessToken = authenticationRepository.getAccessToken()
        val isTokenValid = accessToken?.let { isTokenValid() }?: false
        authenticationRepository.updateIsAuthenticated(value = isTokenValid)
        return isTokenValid
    }

    private suspend fun isTokenValid(): Boolean {
        try {
            searchRepository.searchRepositories(query = "a")
            return true
        } catch (e: Exception) {
            return false
        }
    }
}