package com.jabama.challenge.ui.screen.auth

import com.jabama.challenge.base.BaseViewModel
import com.jabama.challenge.domain.usecase.auth.GetIsAuthenticatedFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val getAuthenticatedFlowUseCase: GetIsAuthenticatedFlowUseCase,
    externalIoDispatcher: CoroutineDispatcher? = null,
    externalScope: CoroutineScope? = null
): BaseViewModel<AuthenticationUiState>(
    externalIoDispatcher = externalIoDispatcher,
    externalScope = externalScope
) {

    override fun createInitialState(): AuthenticationUiState = AuthenticationUiState()

    init {
        startObserveUserAuthenticatedFlow()
    }

    fun onLoginClick() {
        _uiState.update {
            it.copy(shouldNavigateToAuthenticationWithWeb = true)
        }
    }

    private fun startObserveUserAuthenticatedFlow() {
        coroutineScope.launch {
            getAuthenticatedFlowUseCase.execute().collectLatest { isAuthenticated ->
                if (isAuthenticated) {
                    _uiState.update {
                        it.copy(shouldNavigateToSearchScreen = true)
                    }
                }
            }
        }
    }
}