package com.jabama.challenge.ui.screen.splash

import com.jabama.challenge.domain.test.CoroutinesTestRule
import com.jabama.challenge.domain.usecase.IsUserAuthenticatedByValidationUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SplashViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var isUserAuthenticatedByValidationUseCase: IsUserAuthenticatedByValidationUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createViewModel(): SplashViewModel {
        return SplashViewModel(
            isUserAuthenticatedByValidationUseCase = isUserAuthenticatedByValidationUseCase,
            externalIoDispatcher = coroutinesTestRule.testDispatcher,
            externalScope = coroutinesTestRule.testScope
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
    fun `when viewModel created, then shouldNavigateToSearchScreen = false and shouldNavigateToAuthenticationScreen = false`() = runTest {
        val viewModel = createViewModel()
        assertEquals(false, viewModel.uiState.value.shouldNavigateToSearchScreen)
        assertEquals(false, viewModel.uiState.value.shouldNavigateToAuthenticationScreen)
    }

    @Test
    fun `when isUserAuthenticatedByValidationUseCase return false, then shouldNavigateToAuthenticationScreen = true`() = runTest {
        mockIsUserAuthenticatedByValidationUseCase(returnValue = false)
        val viewModel = createViewModel()
        delay(SplashViewModel.SPLASH_WAITING_TIME)
        delay(100)
        assertEquals(true, viewModel.uiState.value.shouldNavigateToAuthenticationScreen)
    }

    @Test
    fun `when isUserAuthenticatedByValidationUseCase return true, then shouldNavigateToSearchScreen = true`() = runTest {
        mockIsUserAuthenticatedByValidationUseCase(returnValue = true)
        val viewModel = createViewModel()
        delay(SplashViewModel.SPLASH_WAITING_TIME)
        delay(100)
        assertEquals(true, viewModel.uiState.value.shouldNavigateToSearchScreen)
    }

    private fun mockIsUserAuthenticatedByValidationUseCase(returnValue: Boolean) {
        coEvery { isUserAuthenticatedByValidationUseCase.execute() } returns returnValue
    }
}