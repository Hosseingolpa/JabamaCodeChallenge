package com.jabama.challenge.domain.model.search

data class Repository(
    val id: String,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val url: String,
)