package com.jabama.challenge.data.remote.search

import com.jabama.challenge.data.model.search.SearchRepositoriesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String
    ): SearchRepositoriesResponseDto
}