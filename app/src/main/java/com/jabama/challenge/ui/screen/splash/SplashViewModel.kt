package com.jabama.challenge.ui.screen.splash

import com.jabama.challenge.base.BaseViewModel
import com.jabama.challenge.domain.usecase.IsUserAuthenticatedByValidationUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val isUserAuthenticatedByValidationUseCase: IsUserAuthenticatedByValidationUseCase,
    externalIoDispatcher: CoroutineDispatcher? = null,
    externalScope: CoroutineScope? = null
): BaseViewModel<SplashUiState>(
    externalIoDispatcher = externalIoDispatcher,
    externalScope = externalScope
) {

    override fun createInitialState(): SplashUiState = SplashUiState()

    companion object {
        const val SPLASH_WAITING_TIME = 3000L
    }

    init {
        startTimerForNavigate()
    }

    private fun startTimerForNavigate() {
        coroutineScope.launch {
            delay(SPLASH_WAITING_TIME)
            checkNavigateToWhichScreen()
        }
    }

    private suspend fun checkNavigateToWhichScreen() {
        if (isAuthenticated()) {
            _uiState.update { it.copy(shouldNavigateToSearchScreen = true) }
        } else {
            _uiState.update { it.copy(shouldNavigateToAuthenticationScreen = true) }
        }
    }

    private suspend fun isAuthenticated(): Boolean {
        return isUserAuthenticatedByValidationUseCase.execute()
    }
}