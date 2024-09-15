package com.jabama.challenge.di

import com.jabama.challenge.data.di.authenticationModule
import com.jabama.challenge.data.di.cacheModule
import com.jabama.challenge.data.di.networkModule

internal val appModules = listOf(
    networkModule,
    authenticationModule,
    cacheModule
)