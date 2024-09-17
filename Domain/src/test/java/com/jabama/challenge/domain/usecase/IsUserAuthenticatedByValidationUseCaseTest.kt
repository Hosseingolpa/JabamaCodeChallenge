package com.jabama.challenge.domain.usecase

import com.jabama.challenge.domain.mock.MockUtil
import com.jabama.challenge.domain.repository.AuthenticationRepository
import com.jabama.challenge.domain.repository.SearchRepository
import com.jabama.challenge.domain.test.CoroutinesTestRule
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

class IsUserAuthenticatedByValidationUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var searchRepository: SearchRepository

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

    private fun createUseCase(): IsUserAuthenticatedByValidationUseCase {
        return IsUserAuthenticatedByValidationUseCase(
            searchRepository = searchRepository,
            authenticationRepository = authenticationRepository
        )
    }

    @Test
    fun `when getAccessToken return null, then use case return false`() = runTest {
        val fakeAccessToken: String? = null
        mockGetAccessToken(returnValue = fakeAccessToken)
        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(false, result)
    }

    @Test
    fun `when getAccessToken return null, then updateIsAuthenticated should call with false value`() = runTest {
        val fakeAccessToken: String? = null
        mockGetAccessToken(returnValue = fakeAccessToken)
        val useCase = createUseCase()
        useCase.execute()

        verify { authenticationRepository.updateIsAuthenticated(value = false) }
    }


    @Test
    fun `when getAccessToken return token and searchRepositories throw exception, then use case return false`() = runTest {
        val fakeAccessToken: String = MockUtil.getRandomString()
        mockGetAccessToken(returnValue = fakeAccessToken)
        coEvery {
            searchRepository.searchRepositories(query = any())
        } coAnswers {
            throw Exception()
        }
        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(false, result)
    }


    @Test
    fun `when getAccessToken return token and searchRepositories throw exception, then updateIsAuthenticated should call with false value`() = runTest {
        val fakeAccessToken: String = MockUtil.getRandomString()
        mockGetAccessToken(returnValue = fakeAccessToken)
        coEvery {
            searchRepository.searchRepositories(query = any())
        } coAnswers {
            throw Exception()
        }
        val useCase = createUseCase()
        useCase.execute()

        verify { authenticationRepository.updateIsAuthenticated(value = false) }
    }

    @Test
    fun `when getAccessToken return token and searchRepositories call successfully, then use case return true`() = runTest {
        val fakeAccessToken: String = MockUtil.getRandomString()
        mockGetAccessToken(returnValue = fakeAccessToken)
        coEvery { searchRepository.searchRepositories(query = any()) } coAnswers {}
        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(true, result)
    }

    @Test
    fun `when getAccessToken return token and searchRepositories call successfully, then updateIsAuthenticated should call with true value`() = runTest {
        val fakeAccessToken: String = MockUtil.getRandomString()
        mockGetAccessToken(returnValue = fakeAccessToken)
        coEvery { searchRepository.searchRepositories(query = any()) } coAnswers {}
        val useCase = createUseCase()
        useCase.execute()

        verify { authenticationRepository.updateIsAuthenticated(value = true) }
    }

    private fun mockGetAccessToken(returnValue: String?) {
        every { authenticationRepository.getAccessToken() } returns returnValue
    }

}