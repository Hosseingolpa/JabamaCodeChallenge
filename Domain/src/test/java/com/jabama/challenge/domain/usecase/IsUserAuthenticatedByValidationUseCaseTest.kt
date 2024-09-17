package com.jabama.challenge.domain.usecase

import com.jabama.challenge.domain.mock.MockUtil
import com.jabama.challenge.domain.mock.repositoryMock
import com.jabama.challenge.domain.model.search.Repository
import com.jabama.challenge.domain.repository.AuthenticationRepository
import com.jabama.challenge.domain.test.CoroutinesTestRule
import com.jabama.challenge.domain.usecase.search.GetRepositoriesUseCase
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
    lateinit var getRepositoriesUseCase: GetRepositoriesUseCase

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
            getRepositoriesUseCase = getRepositoriesUseCase,
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
        mockErrorGetRepositoriesUseCase()
        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(false, result)
    }


    @Test
    fun `when getAccessToken return token and searchRepositories throw exception, then updateIsAuthenticated should call with false value`() = runTest {
        val fakeAccessToken: String = MockUtil.getRandomString()
        mockGetAccessToken(returnValue = fakeAccessToken)
        mockErrorGetRepositoriesUseCase()
        val useCase = createUseCase()
        useCase.execute()

        verify { authenticationRepository.updateIsAuthenticated(value = false) }
    }

    @Test
    fun `when getAccessToken return token and searchRepositories call successfully, then use case return true`() = runTest {
        val fakeAccessToken: String = MockUtil.getRandomString()
        val fakeRepositories = listOf(repositoryMock)
        mockGetAccessToken(returnValue = fakeAccessToken)
        mockGetRepositoriesUseCase(returnValue = fakeRepositories)
        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(true, result)
    }

    @Test
    fun `when getAccessToken return token and searchRepositories call successfully, then updateIsAuthenticated should call with true value`() = runTest {
        val fakeAccessToken: String = MockUtil.getRandomString()
        val fakeRepositories = listOf(repositoryMock)
        mockGetAccessToken(returnValue = fakeAccessToken)
        mockGetRepositoriesUseCase(returnValue = fakeRepositories)
        val useCase = createUseCase()
        useCase.execute()

        verify { authenticationRepository.updateIsAuthenticated(value = true) }
    }

    private fun mockGetAccessToken(returnValue: String?) {
        every { authenticationRepository.getAccessToken() } returns returnValue
    }

    private fun mockErrorGetRepositoriesUseCase(){
        coEvery {
            getRepositoriesUseCase.execute(any())
        } coAnswers {
            throw Exception()
        }
    }

    private fun mockGetRepositoriesUseCase(returnValue: List<Repository>) {
        coEvery {
            getRepositoriesUseCase.execute(any())
        } coAnswers {
            returnValue
        }
    }

}