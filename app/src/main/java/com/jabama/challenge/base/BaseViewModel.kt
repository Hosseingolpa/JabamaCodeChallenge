package com.jabama.challenge.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE>(
    private val externalIoDispatcher: CoroutineDispatcher? = null,
    private val externalScope: CoroutineScope? = null
) : ViewModel() {

    protected val coroutineScope: CoroutineScope
        get() = externalScope?: viewModelScope

    protected val ioDispatcher: CoroutineDispatcher
        get() = externalIoDispatcher?: Dispatchers.IO

    private val initialState: STATE by lazy { createInitialState() }

    abstract fun createInitialState(): STATE

    protected val currentState: STATE
        get() = uiState.value

    protected val _uiState: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected fun <T : Any> useCaseAction(
        scope: CoroutineScope = coroutineScope,
        onLoadingAction: suspend () -> Unit = {},
        action: suspend () -> T,
        onSuccessAction: suspend (T) -> Unit = {},
        onErrorAction: (String) -> Unit = {},
    ): Job {
        return scope.launch(ioDispatcher) {
            try {
                onLoadingAction()
                val result = runCatching { action() }
                val resultValue = result.getOrNull()
                when {
                    result.isFailure -> {
                        onErrorAction(
                            exception = result.exceptionOrNull(),
                            onErrorAction = { message ->
                                onErrorAction(message)
                            }
                        )
                    }

                    resultValue == null -> {
                        onErrorAction(
                            exception = null,
                            onErrorAction = { message ->
                                onErrorAction(message)
                            }
                        )
                    }

                    result.isSuccess -> {
                        onSuccessAction(resultValue)
                    }
                }
            } catch (exception: Exception) {
                onErrorAction(
                    exception = exception,
                    onErrorAction = { message ->
                        onErrorAction(message)
                    }
                )
            }
        }
    }

    private fun onErrorAction(
        exception: Throwable?,
        onErrorAction: (String) -> Unit = {},
    ) {
        if(exception !is CancellationException) {
            onErrorAction(exception?.message ?: "Unknown error")
        }
    }


}