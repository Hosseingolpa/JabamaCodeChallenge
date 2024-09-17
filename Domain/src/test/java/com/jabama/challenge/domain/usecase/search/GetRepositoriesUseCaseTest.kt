package com.jabama.challenge.domain.usecase.search

import com.jabama.challenge.domain.mock.repositoryMock
import com.jabama.challenge.domain.repository.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetRepositoriesUseCaseTest {

    @RelaxedMockK
    lateinit var searchRepository: SearchRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createUseCase(): GetRepositoriesUseCase {
        return GetRepositoriesUseCase(
            searchRepository = searchRepository
        )
    }

    @Test(expected = Exception::class)
    fun `when searchRepositories throw exception, then use case should throw exception`() = runTest {
        coEvery {
            searchRepository.searchRepositories(any())
        } coAnswers {
            throw Exception()
        }
        val useCase = createUseCase()
        useCase.execute("a")
    }

    @Test
    fun `when searchRepositories return value, then use case should return value`() = runTest {
        val fakeRepositories = listOf(repositoryMock)
        coEvery {
            searchRepository.searchRepositories(any())
        } coAnswers {
            fakeRepositories
        }
        val useCase = createUseCase()
        val result = useCase.execute("a")
        assertEquals(fakeRepositories, result)
    }

}