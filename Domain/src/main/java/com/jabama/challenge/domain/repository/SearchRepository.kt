package com.jabama.challenge.domain.repository

import com.jabama.challenge.domain.model.search.Repository

interface SearchRepository {
    suspend fun searchRepositories(query: String): List<Repository>
}