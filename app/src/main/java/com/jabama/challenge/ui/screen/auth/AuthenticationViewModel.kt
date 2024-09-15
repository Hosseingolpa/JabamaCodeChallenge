package com.jabama.challenge.ui.screen.auth

import com.jabama.challenge.base.BaseViewModel
import kotlinx.coroutines.flow.update

class AuthenticationViewModel(): BaseViewModel<AuthenticationUiState>() {

    override fun createInitialState(): AuthenticationUiState = AuthenticationUiState()

    fun onLoginClick() {
        _uiState.update {
            it.copy(shouldNavigateToAuthenticationWithWeb = true)
        }
    }
}