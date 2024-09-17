package com.jabama.challenge.domain.usecase.auth

import com.jabama.challenge.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.StateFlow

class GetIsAuthenticatedFlowUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(): StateFlow<Boolean> {
        return authenticationRepository.getIsAuthenticatedFlow()
    }
}