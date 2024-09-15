package com.jabama.challenge.domain.usecase

import com.jabama.challenge.domain.mock.MockUtil
import com.jabama.challenge.domain.mock.accessTokenMock
import com.jabama.challenge.domain.model.auth.AccessToken
import com.jabama.challenge.domain.repository.AuthenticationRepository
import com.jabama.challenge.domain.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class FetchNewAccessTokenUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

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

    private fun createUseCase(): FetchNewAccessTokenUseCase {
        return FetchNewAccessTokenUseCase(
            authenticationRepository = authenticationRepository
        )
    }

    @Test
    fun `when use case call and return value, then use case should return same value`() = runTest {
        val fakeAccessToken = accessTokenMock
        val fakeCode = MockUtil.getRandomString()
        mockFetchNewAccessToken(
            code = fakeCode,
            returnValue = fakeAccessToken
        )

        val useCase = createUseCase()
        val result = useCase.execute(code = fakeCode)

        assertEquals(fakeAccessToken, result)
    }

    @Test(expected = Exception::class)
    fun `when use case call and throw exception, then use case should throw exception`() = runTest {
        coEvery {
            authenticationRepository.fetchNewAccessToken(any())
        } coAnswers {
            throw Exception()
        }

        val useCase = createUseCase()
        useCase.execute(code = MockUtil.getRandomString())
    }


    private fun mockFetchNewAccessToken(
        code:String,
        returnValue: AccessToken
    ) {
        coEvery {
            authenticationRepository.fetchNewAccessToken(code)
        } coAnswers {
            returnValue
        }
    }
}