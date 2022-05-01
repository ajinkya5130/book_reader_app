package com.ajinkya.bookreaderapp.model

data class BookModel(
    val id: String? = null,
    val name: String? = null,
    val authors: String? = null,
    val notes: String? = null
) {
    fun sampleList(): List<BookModel> {
        return listOf(
            BookModel("1", "Book1", "Author1", "Notes1"),
            BookModel("2", "Book2", "Author2", "Notes2"),
            BookModel("3", "Book3", "Author3", "Notes3"),
        )
    }
}
