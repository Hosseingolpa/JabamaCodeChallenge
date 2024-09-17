package com.jabama.challenge.domain.usecase.auth

import com.jabama.challenge.domain.repository.AuthenticationRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class UpdateIsAuthenticatedUseCaseTest {

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @RelaxedMockK
    lateinit var authenticationRepository: AuthenticationRepository

    private fun createUseCase(): UpdateIsAuthenticatedUseCase {
        return UpdateIsAuthenticatedUseCase(
            authenticationRepository = authenticationRepository
        )
    }

    @Test
    fun `when use case call with true value, then should updateIsAuthenticated with true value`() = runTest {
        val fakeValue = true
        val useCase = createUseCase()
        useCase.execute(value = fakeValue)
        verify {
            authenticationRepository.updateIsAuthenticated(value = fakeValue)
        }
    }

    @Test
    fun `when use case call with false value, then should updateIsAuthenticated with false value`() = runTest {
        val fakeValue = false
        val useCase = createUseCase()
        useCase.execute(value = fakeValue)
        verify {
            authenticationRepository.updateIsAuthenticated(value = fakeValue)
        }
    }
}