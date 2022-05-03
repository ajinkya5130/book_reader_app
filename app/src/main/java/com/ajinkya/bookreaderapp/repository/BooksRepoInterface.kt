package com.ajinkya.bookreaderapp.repository

import com.ajinkya.bookreaderapp.data.ApiResponse
import com.ajinkya.bookreaderapp.model.Item
import com.ajinkya.bookreaderapp.model.OnlineBookModel

interface BooksRepoInterface {
    suspend fun getBooksList(bookName: String): ApiResponse<OnlineBookModel>
    suspend fun getSpecificBook(bookID: String): ApiResponse<Item>
}