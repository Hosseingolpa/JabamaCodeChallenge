package com.jabama.challenge.domain.usecase.search

import com.jabama.challenge.domain.model.search.Repository
import com.jabama.challenge.domain.repository.SearchRepository

class GetRepositoriesUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun execute(query: String): List<Repository> {
        return searchRepository.searchRepositories(query = query)
    }
}