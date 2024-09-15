package com.jabama.challenge.app

import android.app.Application
import com.jabama.challenge.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoinDI()
    }

    private fun startKoinDI() {
        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }
}