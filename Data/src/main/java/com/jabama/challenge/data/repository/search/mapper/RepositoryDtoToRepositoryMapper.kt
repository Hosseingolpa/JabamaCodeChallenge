package com.jabama.challenge.data.repository.search.mapper

import com.jabama.challenge.data.model.search.RepositoryDto
import com.jabama.challenge.domain.model.search.Repository

fun RepositoryDto.mapToRepository(): Repository {
    return Repository(
        id = id,
        name = name,
        fullName = fullName,
        owner = owner.mapToOwner(),
        url = url
    )
}