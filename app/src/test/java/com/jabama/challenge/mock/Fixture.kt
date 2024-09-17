package com.jabama.challenge.mock

import com.jabama.challenge.domain.model.auth.AccessToken
import com.jabama.challenge.domain.model.search.Owner
import com.jabama.challenge.domain.model.search.Repository

val accessTokenMock = AccessToken(
    value = MockUtil.getRandomString(),
    type = MockUtil.getRandomString()
)

val ownerMock = Owner(
    avatarUrl = MockUtil.getRandomString()
)

val repositoryMock = Repository(
    id = MockUtil.getRandomString(),
    name = MockUtil.getRandomString(),
    fullName = MockUtil.getRandomString(),
    owner = ownerMock,
    url = MockUtil.getRandomString(),
)