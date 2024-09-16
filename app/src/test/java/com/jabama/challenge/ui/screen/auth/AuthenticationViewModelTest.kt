package com.jabama.challenge.ui.screen.auth

import org.junit.Test
import kotlin.test.assertEquals

class AuthenticationViewModelTest {

    private fun createViewModel(): AuthenticationViewModel {
        return AuthenticationViewModel()
    }

    @Test
    fun `when view model created, then shouldNavigateToAuthenticationWithWeb = false`() {
        val viewModel = createViewModel()
        assertEquals(false, viewModel.uiState.value.shouldNavigateToAuthenticationWithWeb)
    }


    @Test
    fun `when onLoginClick call, then shouldNavigateToAuthenticationWithWeb = true`() {
        val viewModel = createViewModel()
        viewModel.onLoginClick()
        assertEquals(true, viewModel.uiState.value.shouldNavigateToAuthenticationWithWeb)
    }

}