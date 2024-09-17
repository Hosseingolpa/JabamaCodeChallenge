package com.jabama.challenge.ui.screen.search

import com.jabama.challenge.base.Status
import com.jabama.challenge.domain.model.search.Repository
import com.jabama.challenge.domain.test.CoroutinesTestRule
import com.jabama.challenge.domain.usecase.auth.GetIsAuthenticatedFlowUseCase
import com.jabama.challenge.domain.usecase.search.GetRepositoriesUseCase
import com.jabama.challenge.mock.MockUtil
import com.jabama.challenge.mock.repositoryMock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SearchViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var getRepositoriesUseCase: GetRepositoriesUseCase

    @RelaxedMockK
    lateinit var getIsAuthenticatedFlowUseCase: GetIsAuthenticatedFlowUseCase

    private fun createViewModel(): SearchViewModel {
        return SearchViewModel(
            getRepositoriesUseCase = getRepositoriesUseCase,
            getAuthenticatedFlowUseCase = getIsAuthenticatedFlowUseCase,
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
        coroutinesTestRule.testScope.cancel()
    }

    @Test
    fun `when view model create, then should query empty and repositoriesStatus = not loaded and shouldNavigateToAuthenticationScreen = false`() =
        runTest {
            val viewModel = createViewModel()
            val currentState = viewModel.uiState.value
            assertEquals("", currentState.query)
            assertEquals(Status.getStatusNotLoaded(), currentState.repositoriesStatus)
            assertEquals(false, currentState.shouldNavigateToAuthenticationScreen)
        }

    @Test
    fun `when getAuthenticatedFlowUseCase return true value, then shouldNavigateToAuthenticationScreen = true`() = runTest {
        val fakeAuthFlow = MutableStateFlow(false)
        every { getIsAuthenticatedFlowUseCase.execute() } returns fakeAuthFlow
        val viewModel = createViewModel()
        delay(100)
        assertEquals(true, viewModel.uiState.value.shouldNavigateToAuthenticationScreen)
    }

    @Test
    fun `when onQueryChange call with value, then should query change to this value`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        val viewModel = createViewModel()
        viewModel.onQueryChange(newValue = fakeQuery)
        delay(100)
        assertEquals(fakeQuery, viewModel.uiState.value.query)
    }

    @Test
    fun `when onQueryChange call with value, then repositories status should change to loading`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        val viewModel = createViewModel()
        viewModel.onQueryChange(newValue = fakeQuery)
        delay(100)
        assertEquals(Status.getStatusLoading(), viewModel.uiState.value.repositoriesStatus)
    }

    @Test
    fun `when onQueryChange call with value is empty, then repositories status should change to not loaded`() = runTest {
        val fakeQuery = ""
        val viewModel = createViewModel()
        viewModel.onQueryChange(newValue = fakeQuery)
        delay(100)
        assertEquals(Status.getStatusNotLoaded(), viewModel.uiState.value.repositoriesStatus)
    }

    @Test
    fun `when onQueryChange call with value and getRepositoriesUseCase throw exception, then repositories status should change to error`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        mockErrorGetRepositoriesUseCase()
        val viewModel = createViewModel()
        viewModel.onQueryChange(newValue = fakeQuery)
        delay(1000)
        assertEquals(Status.getStatusError(""), viewModel.uiState.value.repositoriesStatus)
    }

    @Test
    fun `when onQueryChange call with value and getRepositoriesUseCase return value, then repositories status should change to success with same value`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        val fakeRepositories = listOf(repositoryMock)
        mockGetRepositoriesUseCase(returnValue = fakeRepositories, delay = 500)
        val viewModel = createViewModel()
        viewModel.onQueryChange(newValue = fakeQuery)
        delay(1000)
        assertEquals(Status.getStatusSuccess(data = fakeRepositories), viewModel.uiState.value.repositoriesStatus)
    }

    @Test
    fun `when onQueryChange call twice with different value lower 200 milliseconds, then only use case call once`() = runTest {
        val fakeQuery1 = MockUtil.getRandomString(extra = "1")
        val fakeQuery2 = MockUtil.getRandomString(extra = "2")
        val viewModel = createViewModel()
        viewModel.onQueryChange(fakeQuery1)
        delay(100)
        viewModel.onQueryChange(fakeQuery2)

        delay(500)
        coVerify(exactly = 1) { getRepositoriesUseCase.execute(any()) }
    }

    @Test
    fun `when onRetryClick call, then repository status should change to loading immediately`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        val viewModel = createViewModel()
        viewModel.onQueryChange(newValue = fakeQuery)
        delay(1000)
        viewModel.onRetryClick()
        assertEquals(Status.getStatusLoading(), viewModel.uiState.value.repositoriesStatus)
    }

    @Test
    fun `when onRetryClick call and getRepositoriesUseCase throw exception, then repositories status should change to error`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        mockErrorGetRepositoriesUseCase()
        val viewModel = createViewModel()
        viewModel.onQueryChange(newValue = fakeQuery)
        delay(1000)
        viewModel.onRetryClick()
        delay(600)
        assertEquals(Status.getStatusError(""), viewModel.uiState.value.repositoriesStatus)
    }

    @Test
    fun `when onRetryClick call and getRepositoriesUseCase return value, then repositories status should change to success with same value`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        val fakeRepositories = listOf(repositoryMock)
        mockGetRepositoriesUseCase(returnValue = fakeRepositories, delay = 500)
        val viewModel = createViewModel()
        viewModel.onQueryChange(newValue = fakeQuery)
        delay(1000)
        viewModel.onRetryClick()
        delay(600)
        assertEquals(Status.getStatusSuccess(data = fakeRepositories), viewModel.uiState.value.repositoriesStatus)
    }

    private fun mockGetRepositoriesUseCase(
        returnValue: List<Repository>,
        delay: Long? = null,
    ) {
        coEvery { getRepositoriesUseCase.execute(any()) } coAnswers  {
            delay?.let {
                delay(it)
            }
            returnValue
        }
    }

    private fun mockErrorGetRepositoriesUseCase() {
        coEvery { getRepositoriesUseCase.execute(any()) } coAnswers  {
            delay(500)
            throw Exception()
        }
    }
}