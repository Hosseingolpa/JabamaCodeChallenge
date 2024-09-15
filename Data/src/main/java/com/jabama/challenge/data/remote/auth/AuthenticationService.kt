package com.jabama.challenge.data.remote.auth

import com.jabama.challenge.data.model.AccessTokenRequestDto
import com.jabama.challenge.data.model.AccessTokenResponseDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationService {
    @Headers("Accept:application/json")
    @POST("https://github.com/login/oauth/access_token")
    suspend fun accessToken(@Body accessTokenRequest: AccessTokenRequestDto): AccessTokenResponseDto
}