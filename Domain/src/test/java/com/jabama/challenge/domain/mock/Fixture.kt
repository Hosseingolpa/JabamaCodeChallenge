package com.jabama.challenge.domain.mock

import com.jabama.challenge.domain.model.auth.AccessToken

val accessTokenMock = AccessToken(
    value = MockUtil.getRandomString(),
    type = MockUtil.getRandomString()
)