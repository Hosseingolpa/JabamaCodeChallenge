package com.jabama.challenge.data.di

import com.jabama.challenge.data.cache.auth.AuthenticationCache
import com.jabama.challenge.data.cache.auth.AuthenticationCacheImpl
import com.jabama.challenge.data.remote.auth.AuthenticationService
import com.jabama.challenge.data.repository.auth.AuthenticationRepositoryImpl
import com.jabama.challenge.domain.repository.AuthenticationRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val authenticationDataModule = module {
    single {
        get<Retrofit>(named(RETROFIT))
            .create(AuthenticationService::class.java)
    }

    single<AuthenticationCache> {
        AuthenticationCacheImpl(
            sharedPreferences = get()
        )
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            service = get(),
            cache = get()
        )
    }
}