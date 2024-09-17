package com.jabama.challenge.ui.screen.auth

import com.jabama.challenge.domain.test.CoroutinesTestRule
import com.jabama.challenge.domain.usecase.auth.GetIsAuthenticatedFlowUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class AuthenticationViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var getIsAuthenticatedFlowUseCase: GetIsAuthenticatedFlowUseCase

    private fun createViewModel(): AuthenticationViewModel {
        return AuthenticationViewModel(
            getAuthenticatedFlowUseCase = getIsAuthenticatedFlowUseCase
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

    @Test
    fun `when view model created, then shouldNavigateToAuthenticationWithWeb = false`() {
        val viewModel = createViewModel()
        assertEquals(false, viewModel.uiState.value.shouldNavigateToAuthenticationWithWeb)
    }


    @Test
    fun `when onLoginClick call, then shouldNavigateToAuthenticationWithWeb = true`() {
        val viewModel = createViewModel()
        viewModel.onLoginClick()
        assertEquals(true, viewModel.uiState.value.shouldNavigateToAuthenticationWithWeb)
    }

    @Test
    fun `when getAuthenticatedFlowUseCase return true value, then shouldNavigateToSearchScreen = true`() = runTest {
        coEvery { getIsAuthenticatedFlowUseCase.execute() } returns MutableStateFlow(value = true)
        val viewModel = createViewModel()
        delay(500)
        assertEquals(true, viewModel.uiState.value.shouldNavigateToSearchScreen)
    }

}