package com.jabama.challenge.data.remote.auth

import com.jabama.challenge.data.model.auth.AccessTokenRequestDto
import com.jabama.challenge.data.model.auth.AccessTokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("https://github.com/login/oauth/access_token")
    suspend fun fetchNewAccessToken(@Body accessTokenRequest: AccessTokenRequestDto): AccessTokenResponseDto
}