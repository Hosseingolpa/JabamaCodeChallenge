package com.jabama.challenge.data.repository.auth

import com.jabama.challenge.data.cache.auth.AuthenticationCache
import com.jabama.challenge.data.mock.MockUtil
import com.jabama.challenge.data.mock.accessTokenResponseDtoMock
import com.jabama.challenge.data.remote.auth.AuthenticationService
import com.jabama.challenge.data.repository.auth.mapper.mapToAccessToken
import com.jabama.challenge.data.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class AuthenticationRepositoryImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var authenticationService: AuthenticationService

    @RelaxedMockK
    lateinit var authenticationCache: AuthenticationCache

    private fun createRepository(): AuthenticationRepositoryImpl {
        return AuthenticationRepositoryImpl(
            service = authenticationService,
            cache = authenticationCache
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test(expected = Exception::class)
    fun `when fetchNewAccessToken call and service throw exception, then should throw exception`() =
        runTest {
            coEvery {
                authenticationService.fetchNewAccessToken(any())
            } coAnswers {
                throw Exception()
            }

            val repository = createRepository()
            repository.fetchNewAccessToken(code = "")
        }

    @Test
    fun `when fetchNewAccessToken call and service return access token response, then should access token that mapped`() =
        runTest {
            val fakeAccessTokenResponse = accessTokenResponseDtoMock
            coEvery {
                authenticationService.fetchNewAccessToken(any())
            } coAnswers {
                fakeAccessTokenResponse
            }
            mockSaveAccessTokenFromCache(inputValue = fakeAccessTokenResponse.accessToken)

            val repository = createRepository()
            val result = repository.fetchNewAccessToken(code = "")

            assertEquals(fakeAccessTokenResponse.mapToAccessToken(), result)
        }

    @Test
    fun `when fetchNewAccessToken call and service return access token response, then should saveAccessToken call`() =
        runTest {
            val fakeAccessTokenResponse = accessTokenResponseDtoMock
            coEvery {
                authenticationService.fetchNewAccessToken(any())
            } coAnswers {
                fakeAccessTokenResponse
            }

            val repository = createRepository()
            repository.fetchNewAccessToken(code = "")

            verify { authenticationCache.saveAccessToken(value = fakeAccessTokenResponse.accessToken) }
        }

    @Test
    fun `when getAccessToken call and cache getAccessToken return null, then should return null`() {
        val fakeToken: String? = null
        mockGetAccessTokenFromCache(returnValue = fakeToken)
        val repository = createRepository()

        val result = repository.getAccessToken()
        assertEquals(fakeToken, result)
    }

    @Test
    fun `when getAccessToken call and cache getAccessToken return value, then should return value`() {
        val fakeToken: String = MockUtil.getRandomString()
        mockGetAccessTokenFromCache(returnValue = fakeToken)
        val repository = createRepository()

        val result = repository.getAccessToken()
        assertEquals(fakeToken, result)
    }

    @Test
    fun `when repository created, then isAuthenticated should return false`() {
        val repository = createRepository()
        assertEquals(false, repository.getIsAuthenticatedFlow().value)
    }


    @Test
    fun `when updateIsAuthenticated with true value,then isAuthenticated should return true`() {
        val isAuthenticated = true
        val repository = createRepository()
        repository.updateIsAuthenticated(value = isAuthenticated)
        assertEquals(isAuthenticated, repository.getIsAuthenticatedFlow().value)
    }


    @Test
    fun `when updateIsAuthenticated with true false,then isAuthenticated should return false`() {
        val isAuthenticated = false
        val repository = createRepository()
        repository.updateIsAuthenticated(value = isAuthenticated)
        assertEquals(isAuthenticated, repository.getIsAuthenticatedFlow().value)
    }

    private fun mockSaveAccessTokenFromCache(inputValue: String) {
        every { authenticationCache.saveAccessToken(value = inputValue) } returns Unit
    }

    private fun mockGetAccessTokenFromCache(returnValue: String?) {
        every { authenticationCache.getAccessToken() } returns returnValue
    }

}