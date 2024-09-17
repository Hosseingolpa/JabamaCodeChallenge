package com.jabama.challenge.data.mock

import com.jabama.challenge.data.model.auth.AccessTokenResponseDto
import com.jabama.challenge.data.model.search.OwnerDto
import com.jabama.challenge.data.model.search.RepositoryDto
import com.jabama.challenge.data.model.search.SearchRepositoriesResponseDto

val accessTokenResponseDtoMock = AccessTokenResponseDto(
    accessToken = MockUtil.getRandomString(),
    tokenType = MockUtil.getRandomString()
)

val searchRepositoriesResponseDtoMock = SearchRepositoriesResponseDto(
    repositories = listOf()
)

val ownerDtoMock = OwnerDto(
    avatarUrl = MockUtil.getRandomString()
)

val repositoryDtoMock = RepositoryDto(
    id = MockUtil.getRandomString(),
    name = MockUtil.getRandomString(),
    fullName = MockUtil.getRandomString(),
    owner = ownerDtoMock,
    url = MockUtil.getRandomString(),
)


