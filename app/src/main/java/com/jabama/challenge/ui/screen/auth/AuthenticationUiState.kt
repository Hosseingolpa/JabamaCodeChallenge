package com.jabama.challenge.ui.screen.auth

data class AuthenticationUiState(
    val shouldNavigateToAuthenticationWithWeb: Boolean = false,
    val shouldNavigateToSearchScreen: Boolean = false
)
