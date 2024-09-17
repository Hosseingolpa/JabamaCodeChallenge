package com.jabama.challenge.data.model.auth

import com.google.gson.annotations.SerializedName

data class AccessTokenResponseDto(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String
)
