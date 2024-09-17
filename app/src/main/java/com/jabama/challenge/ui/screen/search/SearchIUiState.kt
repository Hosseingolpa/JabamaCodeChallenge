package com.jabama.challenge.ui.screen.search

import com.jabama.challenge.base.Status
import com.jabama.challenge.domain.model.search.Repository

data class SearchIUiState(
    val query: String = "",
    val repositoriesStatus: Status<List<Repository>> = Status.getStatusNotLoaded(),
    val shouldNavigateToAuthenticationScreen: Boolean = false
)
