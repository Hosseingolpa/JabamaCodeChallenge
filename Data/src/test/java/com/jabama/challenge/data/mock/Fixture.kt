package com.jabama.challenge.data.mock

import com.jabama.challenge.data.model.auth.AccessTokenResponseDto
import com.jabama.challenge.data.model.search.SearchRepositoriesResponseDto

val accessTokenResponseDtoMock = AccessTokenResponseDto(
    accessToken = MockUtil.getRandomString(),
    tokenType = MockUtil.getRandomString()
)

val searchRepositoriesResponseDtoMock = SearchRepositoriesResponseDto(
    totalCount = 0
)
