package com.jabama.challenge.data.model.search

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("avatar_url")
    val avatarUrl: String
)
