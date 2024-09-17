package com.jabama.challenge.ui.screen.loginsucceeded

import com.jabama.challenge.mock.MockUtil
import com.jabama.challenge.mock.accessTokenMock
import com.jabama.challenge.domain.model.auth.AccessToken
import com.jabama.challenge.domain.test.CoroutinesTestRule
import com.jabama.challenge.domain.usecase.auth.FetchNewAccessTokenUseCase
import com.jabama.challenge.domain.usecase.auth.UpdateIsAuthenticatedUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class LoginSucceedViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var fetchNewAccessTokenUseCase: FetchNewAccessTokenUseCase

    @RelaxedMockK
    lateinit var updateIsAuthenticatedUseCase: UpdateIsAuthenticatedUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createViewModel(): LoginSucceedViewModel {
        return LoginSucceedViewModel(
            fetchNewAccessTokenUseCase = fetchNewAccessTokenUseCase,
            updateIsAuthenticatedUseCase = updateIsAuthenticatedUseCase,
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
    fun `when view model created, then accessTokenState should in loading state`() {
        val viewModel = createViewModel()
        assertEquals(AccessTokenState.Loading, viewModel.uiState.value.accessTokenState)
    }

    @Test
    fun `when updateCode call and use case return value, then accessTokenState should equals Success`() = runTest {
        val fakeCode = MockUtil.getRandomString()
        val fakeAccessToken = accessTokenMock
        mockFetchNewAccessTokenUseCase(
            inputValue = fakeCode,
            returnValue = fakeAccessToken
        )
        val viewModel = createViewModel()
        viewModel.updateCode(code = fakeCode)
        delay(500)
        assertEquals(AccessTokenState.Success, viewModel.uiState.value.accessTokenState)
    }


    @Test
    fun `when updateCode call and use case throw exception, then accessTokenState should equals Error`() = runTest {
        val fakeCode = MockUtil.getRandomString()
        coEvery { fetchNewAccessTokenUseCase.execute(fakeCode) } coAnswers {
            throw Exception()
        }
        val viewModel = createViewModel()
        viewModel.updateCode(code = fakeCode)
        delay(500)
        assertEquals(AccessTokenState.Error, viewModel.uiState.value.accessTokenState)
    }


    @Test
    fun `when updateCode call and use case return value, then should updateIsAuthenticatedUseCase call with true value`() = runTest {
        val fakeCode = MockUtil.getRandomString()
        val fakeAccessToken = accessTokenMock
        mockFetchNewAccessTokenUseCase(
            inputValue = fakeCode,
            returnValue = fakeAccessToken
        )
        val viewModel = createViewModel()
        viewModel.updateCode(code = fakeCode)
        delay(500)
        verify { updateIsAuthenticatedUseCase.execute(value = true) }
    }


    @Test
    fun `when updateCode call and use case throw exception, then should updateIsAuthenticatedUseCase call with false value`() = runTest {
        val fakeCode = MockUtil.getRandomString()
        coEvery { fetchNewAccessTokenUseCase.execute(fakeCode) } coAnswers {
            throw Exception()
        }
        val viewModel = createViewModel()
        viewModel.updateCode(code = fakeCode)
        delay(500)
        verify { updateIsAuthenticatedUseCase.execute(value = false) }
    }


    private fun mockFetchNewAccessTokenUseCase(
        inputValue: String,
        returnValue: AccessToken
    ) {
        coEvery { fetchNewAccessTokenUseCase.execute(inputValue) } returns returnValue
    }

}