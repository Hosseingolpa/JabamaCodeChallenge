package com.jabama.challenge.data.mock

import com.jabama.challenge.data.model.AccessTokenResponseDto

val accessTokenResponseDtoMock = AccessTokenResponseDto(
    accessToken = MockUtil.getRandomString(),
    tokenType = MockUtil.getRandomString()
)
