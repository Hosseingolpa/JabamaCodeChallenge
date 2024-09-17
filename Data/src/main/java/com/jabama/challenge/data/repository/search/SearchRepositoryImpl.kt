package com.jabama.challenge.data.repository.search

import com.jabama.challenge.data.remote.search.SearchService
import com.jabama.challenge.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val searchService: SearchService
): SearchRepository {
    override suspend fun searchRepositories(query: String) {
        searchService.searchRepositories(query = query)
    }
}