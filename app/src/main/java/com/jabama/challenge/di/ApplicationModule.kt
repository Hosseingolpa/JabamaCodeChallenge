package com.jabama.challenge.di

import com.jabama.challenge.data.di.authenticationDataModule
import com.jabama.challenge.data.di.cacheModule
import com.jabama.challenge.data.di.networkModule
import com.jabama.challenge.domain.di.authenticationDomainModule
import com.jabama.challenge.ui.di.viewModelModule

internal val appModules = listOf(
    networkModule,
    authenticationDataModule,
    authenticationDomainModule,
    cacheModule,
    viewModelModule
)