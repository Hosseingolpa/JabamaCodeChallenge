package com.jabama.challenge.data.repository.auth.mapper

import com.jabama.challenge.data.model.auth.AccessTokenResponseDto
import com.jabama.challenge.domain.model.auth.AccessToken

fun AccessTokenResponseDto.mapToAccessToken(): AccessToken {
    return AccessToken(
        value = accessToken,
        type = tokenType
    )
}