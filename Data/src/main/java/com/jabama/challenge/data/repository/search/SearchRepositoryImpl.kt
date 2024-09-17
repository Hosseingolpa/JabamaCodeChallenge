package com.jabama.challenge.data.repository.search

import com.jabama.challenge.data.remote.search.SearchService
import com.jabama.challenge.data.repository.search.mapper.mapToRepository
import com.jabama.challenge.domain.model.search.Repository
import com.jabama.challenge.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val searchService: SearchService
): SearchRepository {
    override suspend fun searchRepositories(query: String): List<Repository> {
        val searchResponse = searchService.searchRepositories(query = query)
        return searchResponse.repositories.map { it.mapToRepository() }
    }
}