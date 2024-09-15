package com.jabama.challenge.domain.di

import com.jabama.challenge.domain.usecase.FetchNewAccessTokenUseCase
import com.jabama.challenge.domain.usecase.GetAccessTokenUseCase
import org.koin.dsl.module


val authenticationDomainModule = module {

    factory {
        GetAccessTokenUseCase(
            authenticationRepository = get()
        )
    }

    factory {
        FetchNewAccessTokenUseCase(
            authenticationRepository = get()
        )
    }

}