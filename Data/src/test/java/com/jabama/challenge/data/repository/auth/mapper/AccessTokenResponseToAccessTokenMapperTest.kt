package com.jabama.challenge.data.repository.auth.mapper

import com.jabama.challenge.data.mock.accessTokenResponseDtoMock
import org.junit.Test
import kotlin.test.assertEquals

class AccessTokenResponseToAccessTokenMapperTest {

    @Test
    fun `accessTokenResponseToAccessTokenMapper work correctly`() {
        val responseMock = accessTokenResponseDtoMock
        val accessToken = responseMock.mapToAccessToken()

        assertEquals(responseMock.accessToken, accessToken.value)
        assertEquals(responseMock.tokenType, accessToken.type)
    }

}