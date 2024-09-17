package com.jabama.challenge.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchRepositoriesResponseDto(
    @SerializedName("total_count")
    var totalCount: Int,
)
