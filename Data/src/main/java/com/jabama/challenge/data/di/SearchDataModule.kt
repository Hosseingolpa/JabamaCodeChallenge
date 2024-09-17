package com.jabama.challenge.data.di

import com.jabama.challenge.data.remote.search.SearchService
import com.jabama.challenge.data.repository.search.SearchRepositoryImpl
import com.jabama.challenge.domain.repository.SearchRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val searchDataModule = module {

    single {
        get<Retrofit>(named(RETROFIT))
            .create(SearchService::class.java)
    }

    single <SearchRepository> {
        SearchRepositoryImpl(
            searchService = get()
        )
    }
}