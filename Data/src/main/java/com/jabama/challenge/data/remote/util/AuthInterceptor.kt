package com.jabama.challenge.data.remote.util

import com.jabama.challenge.data.cache.auth.AccessTokenAuthenticationCache
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val accessTokenAuthenticationCache: AccessTokenAuthenticationCache
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val accessToken = accessTokenAuthenticationCache.getAccessToken()
        val newRequest = originalRequest.newBuilder().apply {
            addHeader("Accept", "application/vnd.github+json")
            addHeader("Accept", "application/json")
            if (accessToken != null) {
                addHeader("Authorization", "Bearer $accessToken") // Token from the provider
            }
            addHeader("X-GitHub-Api-Version", "2022-11-28")
        }.build()

        return chain.proceed(newRequest)
    }
}