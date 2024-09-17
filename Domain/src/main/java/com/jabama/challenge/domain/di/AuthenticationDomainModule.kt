package com.jabama.challenge.domain.di

import com.jabama.challenge.domain.usecase.IsUserAuthenticatedByValidationUseCase
import com.jabama.challenge.domain.usecase.auth.FetchNewAccessTokenUseCase
import com.jabama.challenge.domain.usecase.auth.GetAccessTokenUseCase
import com.jabama.challenge.domain.usecase.auth.GetIsAuthenticatedFlowUseCase
import com.jabama.challenge.domain.usecase.auth.UpdateIsAuthenticatedUseCase
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

    factory {
        IsUserAuthenticatedByValidationUseCase(
            authenticationRepository = get(),
            searchRepository = get()
        )
    }

    factory {
        UpdateIsAuthenticatedUseCase(
            authenticationRepository = get()
        )
    }

    factory {
        GetIsAuthenticatedFlowUseCase(
            authenticationRepository = get()
        )
    }

}