package com.jabama.challenge.data.repository.search

import com.jabama.challenge.data.mock.MockUtil
import com.jabama.challenge.data.mock.searchRepositoriesResponseDtoMock
import com.jabama.challenge.data.remote.search.SearchService
import com.jabama.challenge.data.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchRepositoryImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var searchService: SearchService

    private fun createRepository(): SearchRepositoryImpl {
        return SearchRepositoryImpl(
            searchService = searchService
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
    fun `when searchRepositories call and service throw exception, then repository should throw exception`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        coEvery { searchService.searchRepositories(query = fakeQuery) } coAnswers {
            throw Exception()
        }
        val repository = createRepository()
        repository.searchRepositories(query = fakeQuery)
    }

    @Test
    fun `when searchRepositories call and service return value, then repository not return anything and no error should not happen`() = runTest {
        val fakeQuery = MockUtil.getRandomString()
        val returnValue = searchRepositoriesResponseDtoMock
        coEvery { searchService.searchRepositories(query = fakeQuery) } coAnswers {
            returnValue
        }
        val repository = createRepository()
        repository.searchRepositories(query = fakeQuery)
    }


}