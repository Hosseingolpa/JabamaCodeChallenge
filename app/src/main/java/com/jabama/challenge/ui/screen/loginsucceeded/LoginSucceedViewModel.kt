package com.jabama.challenge.ui.screen.loginsucceeded

import com.jabama.challenge.base.BaseViewModel
import com.jabama.challenge.domain.usecase.FetchNewAccessTokenUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update

class LoginSucceedViewModel(
    private val fetchNewAccessTokenUseCase: FetchNewAccessTokenUseCase,
    externalIoDispatcher: CoroutineDispatcher? = null,
    externalScope: CoroutineScope? = null
) : BaseViewModel<LoginSucceedUiState>(
    externalIoDispatcher = externalIoDispatcher,
    externalScope = externalScope
) {

    override fun createInitialState(): LoginSucceedUiState = LoginSucceedUiState()

    fun updateCode(code: String) {
        useCaseAction(
            action = {
                fetchNewAccessTokenUseCase.execute(code = code)
            },
            onSuccessAction = {
                _uiState.update { it.copy(accessTokenState = AccessTokenState.Success) }
            },
            onErrorAction = {
                _uiState.update { it.copy(accessTokenState = AccessTokenState.Error) }
            }
        )
    }
}