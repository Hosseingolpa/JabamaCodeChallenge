package com.jabama.challenge.data.di

import com.jabama.challenge.data.cache.auth.AuthenticationCache
import com.jabama.challenge.data.cache.auth.AuthenticationCacheImpl
import com.jabama.challenge.data.remote.auth.AuthenticationService
import com.jabama.challenge.data.repository.auth.AuthenticationRepositoryImpl
import com.jabama.challenge.domain.repository.AuthenticationRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val authenticationModule = module {
    factory {
        get<Retrofit>(named(RETROFIT))
            .create(AuthenticationService::class.java)
    }

    factory<AuthenticationCache> {
        AuthenticationCacheImpl(
            sharedPreferences = get()
        )
    }

    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            service = get(),
            cache = get()
        )
    }
}