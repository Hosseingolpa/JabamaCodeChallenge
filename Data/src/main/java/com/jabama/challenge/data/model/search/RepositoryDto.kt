package com.jabama.challenge.data.model.search

import com.google.gson.annotations.SerializedName

data class RepositoryDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: OwnerDto,
    @SerializedName("html_url")
    val url: String,
)