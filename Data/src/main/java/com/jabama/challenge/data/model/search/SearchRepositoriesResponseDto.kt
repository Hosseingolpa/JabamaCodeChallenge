package com.jabama.challenge.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchRepositoriesResponseDto(
    @SerializedName("items")
    val repositories: List<RepositoryDto>
)
