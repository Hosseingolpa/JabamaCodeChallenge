package com.jabama.challenge.ui.screen.search

import android.util.Log
import com.jabama.challenge.base.BaseViewModel
import com.jabama.challenge.base.Status
import com.jabama.challenge.domain.model.search.Repository
import com.jabama.challenge.domain.usecase.auth.GetIsAuthenticatedFlowUseCase
import com.jabama.challenge.domain.usecase.search.GetRepositoriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val getAuthenticatedFlowUseCase: GetIsAuthenticatedFlowUseCase,
    externalIoDispatcher: CoroutineDispatcher? = null,
    externalScope: CoroutineScope? = null
) : BaseViewModel<SearchIUiState>(
    externalIoDispatcher = externalIoDispatcher,
    externalScope = externalScope
) {

    override fun createInitialState(): SearchIUiState = SearchIUiState()
    private var searchJob: Job? = null

    init {
        startObserveUserAuthenticatedFlow()
        startObserveOnQueryChangeForSearch()
    }

    fun onQueryChange(newValue: String) {
        updateQuery(newValue = newValue)
    }

    fun onRetryClick() {
        cancelSearchPreviousJob()
        updateRepositoriesStatus(Status.getStatusLoading())
        startSearch(query = uiState.value.query)
    }

    private fun startObserveUserAuthenticatedFlow() {
        coroutineScope.launch {
            getAuthenticatedFlowUseCase.execute().collectLatest { isAuthenticated ->
                if (!isAuthenticated) {
                    _uiState.update {
                        it.copy(shouldNavigateToAuthenticationScreen = true)
                    }
                }
            }
        }
    }

    private fun updateQuery(newValue: String) {
        _uiState.update { it.copy(query = newValue) }
    }

    private fun startObserveOnQueryChangeForSearch() {
        coroutineScope.launch {
            getQueryFlow()
                .onEach {
                    cancelSearchPreviousJob()
                    updateRepositoriesStatus(Status.getStatusLoading())
                }
                .filter {
                    val isNotEmpty = it.isNotEmpty()
                    if (!isNotEmpty) {
                        updateRepositoriesStatus(Status.getStatusNotLoaded())
                    }
                    isNotEmpty
                }
                .debounce(200)
                .collectLatest { query ->
                    startSearch(query = query)
                }
        }
    }

    private fun getQueryFlow(): Flow<String> {
        return _uiState
            .map { it.query }
            .distinctUntilChanged()
    }

    private fun updateRepositoriesStatus(newStatus: Status<List<Repository>>) {
        _uiState.update {
            it.copy(repositoriesStatus = newStatus)
        }
    }

    private fun cancelSearchPreviousJob() {
        searchJob?.cancel()
    }

    private fun startSearch(query: String) {
        searchJob = useCaseAction(
            action = {
                getRepositoriesUseCase.execute(query)
            },
            onSuccessAction = { result ->
                updateRepositoriesStatus(Status.getStatusSuccess(data = result))
            },
            onErrorAction = {
                updateRepositoriesStatus(Status.getStatusError(""))
            }
        )
    }


}