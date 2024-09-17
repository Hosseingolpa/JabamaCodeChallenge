package com.jabama.challenge.domain.repository

interface SearchRepository {
    suspend fun searchRepositories(query: String)
}