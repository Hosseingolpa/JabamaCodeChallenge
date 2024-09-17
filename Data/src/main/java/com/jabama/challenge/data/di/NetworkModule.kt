package com.jabama.challenge.data.di

import com.jabama.challenge.data.remote.util.AuthInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single(named(READ_TIMEOUT)) { 30 * 1000L }
    single(named(WRITE_TIMEOUT)) { 10 * 1000L }
    single(named(CONNECTION_TIMEOUT)) { 10 * 1000L }
    single(named(GITHUB_BASE_URL)) { "https://api.github.com/" }

    factory<Interceptor>(named(LOG_INTERCEPTOR)) {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory<Interceptor>(named(AUTHENTICATION_INTERCEPTOR)) {
        AuthInterceptor(
            accessTokenAuthenticationCache = get()
        )
    }

    factory(named(OK_HTTP)) {
        OkHttpClient.Builder()
            .readTimeout(get(named(READ_TIMEOUT)), TimeUnit.MILLISECONDS)
            .writeTimeout(get(named(WRITE_TIMEOUT)), TimeUnit.MILLISECONDS)
            .connectTimeout(get(named(CONNECTION_TIMEOUT)), TimeUnit.MILLISECONDS)
            .addInterceptor(get<Interceptor>(named(LOG_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(named(AUTHENTICATION_INTERCEPTOR)))
            .build()
    }

    factory {
        GsonConverterFactory.create()
    }

    factory {
        CoroutineCallAdapterFactory()
    }

    single(named(RETROFIT)) {
        Retrofit.Builder()
            .client(get(named(OK_HTTP)))
            .baseUrl(get<String>(named(GITHUB_BASE_URL)))
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
            .build()
    }
}