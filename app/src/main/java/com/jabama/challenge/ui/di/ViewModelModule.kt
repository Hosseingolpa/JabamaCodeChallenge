package com.jabama.challenge.ui.di

import com.jabama.challenge.ui.screen.auth.AuthenticationViewModel
import com.jabama.challenge.ui.screen.loginsucceeded.LoginSucceedViewModel
import com.jabama.challenge.ui.screen.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SplashViewModel()
    }

    viewModel {
        AuthenticationViewModel()
    }

    viewModel {
        LoginSucceedViewModel(
            fetchNewAccessTokenUseCase = get()
        )
    }
}