package com.jabama.challenge.domain.usecase.auth

import com.jabama.challenge.domain.repository.AuthenticationRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetIsAuthenticatedFlowUseCaseTest() {

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

    private fun createUseCase(): GetIsAuthenticatedFlowUseCase {
        return GetIsAuthenticatedFlowUseCase(
            authenticationRepository = authenticationRepository
        )
    }

    @Test
    fun `when getIsAuthenticatedFlow return state flow with true value, then use case should return same state flow`() {
        val fakeFlow = MutableStateFlow(true)
        mockGetIsAuthenticatedFlow(returnValue = fakeFlow)
        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(fakeFlow, result)
    }

    @Test
    fun `when getIsAuthenticatedFlow return state flow with false value, then use case should return same state flow`() {
        val fakeFlow = MutableStateFlow(false)
        mockGetIsAuthenticatedFlow(returnValue = fakeFlow)
        val useCase = createUseCase()
        val result = useCase.execute()

        assertEquals(fakeFlow, result)
    }

    private fun mockGetIsAuthenticatedFlow(returnValue: StateFlow<Boolean>) {
        every { authenticationRepository.getIsAuthenticatedFlow() } returns returnValue
    }

}