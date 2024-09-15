package com.jabama.challenge.domain.usecase

import com.jabama.challenge.domain.repository.AuthenticationRepository
import com.jabama.challenge.domain.mock.MockUtil
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetAccessTokenUseCaseTest {

    @RelaxedMockK
    lateinit var authenticationRepository: AuthenticationRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createUseCase(): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(
            authenticationRepository = authenticationRepository
        )
    }

    @Test
    fun `when use case call and getAccessToken from repository return value, then use case should return value`() {
        val fakeAccessToken = MockUtil.getRandomString()
        mockGetAccessTokenFromRepository(returnValue = fakeAccessToken)

        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(fakeAccessToken, result)
    }

    @Test
    fun `when use case call and getAccessToken from repository return null, then use case should return null`() {
        val fakeAccessToken: String? = null
        mockGetAccessTokenFromRepository(returnValue = fakeAccessToken)

        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(fakeAccessToken, result)
    }

    private fun mockGetAccessTokenFromRepository(returnValue: String?) {
        every { authenticationRepository.getAccessToken() } returns returnValue
    }

}