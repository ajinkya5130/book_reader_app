package com.ajinkya.bookreaderapp.model

data class OnlineBookModel(
    val items: List<Item>? = emptyList(),
    val kind: String? = null,
    val totalItems: Int? = null
)