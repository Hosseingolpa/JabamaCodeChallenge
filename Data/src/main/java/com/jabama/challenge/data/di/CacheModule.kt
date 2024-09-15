package com.jabama.challenge.data.di

import androidx.preference.PreferenceManager
import org.koin.dsl.module

val cacheModule = module {

    single {
        PreferenceManager.getDefaultSharedPreferences(get())
    }

}