package com.jabama.challenge.data.repository.search.mapper

import com.jabama.challenge.data.mock.ownerDtoMock
import org.junit.Test
import kotlin.test.assertEquals

class OwnerDtoToOwnerMapperTest {

    @Test
    fun `OwnerDtoToOwnerMapper work correctly`() {
        val fakeOwner = ownerDtoMock
        val result = ownerDtoMock.mapToOwner()
        assertEquals(fakeOwner.avatarUrl, result.avatarUrl)
    }
}