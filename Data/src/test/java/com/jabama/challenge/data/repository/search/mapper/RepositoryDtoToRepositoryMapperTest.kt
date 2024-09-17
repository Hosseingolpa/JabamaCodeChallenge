package com.jabama.challenge.data.repository.search.mapper

import com.jabama.challenge.data.mock.repositoryDtoMock
import org.junit.Test
import kotlin.test.assertEquals

class RepositoryDtoToRepositoryMapperTest {

    @Test
    fun `RepositoryDtoToRepositoryMapper work correctly`() {
        val fakeRepository = repositoryDtoMock
        val result = fakeRepository.mapToRepository()
        assertEquals(fakeRepository.id, result.id)
        assertEquals(fakeRepository.name, result.name)
        assertEquals(fakeRepository.fullName, result.fullName)
        assertEquals(fakeRepository.owner.avatarUrl, result.owner.avatarUrl)
        assertEquals(fakeRepository.url, result.url)
    }
}