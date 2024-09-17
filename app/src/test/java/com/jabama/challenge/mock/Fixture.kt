package com.jabama.challenge.domain.mock

import com.jabama.challenge.domain.model.auth.AccessToken
import com.jabama.challenge.mock.MockUtil

val accessTokenMock = AccessToken(
    value = MockUtil.getRandomString(),
    type = MockUtil.getRandomString()
)