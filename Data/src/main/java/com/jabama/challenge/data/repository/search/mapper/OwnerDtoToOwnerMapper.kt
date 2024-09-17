package com.jabama.challenge.data.repository.search.mapper

import com.jabama.challenge.data.model.search.OwnerDto
import com.jabama.challenge.domain.model.search.Owner

fun OwnerDto.mapToOwner(): Owner {
    return Owner(avatarUrl = avatarUrl)
}