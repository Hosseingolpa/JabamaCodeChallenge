package com.jabama.challenge.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.jabama.challenge.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(): BaseViewModel<SplashUiState>() {

    override fun createInitialState(): SplashUiState = SplashUiState()

    companion object {
        private const val SPLASH_WAITING_TIME = 4000L
    }

    init {
        startTimerForNavigate()
    }

    private fun startTimerForNavigate() {
        viewModelScope.launch {
            delay(SPLASH_WAITING_TIME)
            checkNavigateToWhichScreen()
        }
    }

    private fun checkNavigateToWhichScreen() {
        if (isAuthenticated()) {
            _uiState.update { it.copy(shouldNavigateToSearchScreen = true) }
        } else {
            _uiState.update { it.copy(shouldNavigateToAuthenticationScreen = true) }
        }
    }

    private fun isAuthenticated(): Boolean {
        return false
    }
}