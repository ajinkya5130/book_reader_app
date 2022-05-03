package com.ajinkya.bookreaderapp.repository

import com.ajinkya.bookreaderapp.data.ApiResponse
import com.ajinkya.bookreaderapp.model.Item
import com.ajinkya.bookreaderapp.model.OnlineBookModel
import com.ajinkya.bookreaderapp.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val booksApi: BooksApi) : BooksRepoInterface {

    override suspend fun getBooksList(bookName: String): ApiResponse<OnlineBookModel> {
        return try {
            val apiRes = booksApi.getBooks(bookName = bookName)
            ApiResponse.Loading("Loading...")
            if (apiRes.isSuccessful) {
                ApiResponse.Success(data = apiRes.body()!!)
            } else {
                ApiResponse.Error(apiRes.errorBody().toString())
            }
        } catch (ex: Exception) {
            ApiResponse.Error(ex.message!!)
        }
    }

    override suspend fun getSpecificBook(bookID: String): ApiResponse<Item> {
        return try {
            val apiRes = booksApi.getPerticularBook(bookID)
            ApiResponse.Loading("Loading...")
            if (apiRes.isSuccessful) {
                ApiResponse.Success(data = apiRes.body()!!)
            } else {
                ApiResponse.Error(apiRes.errorBody().toString())
            }

        } catch (ex: Exception) {
            ApiResponse.Error(ex.message!!)
        }
    }
}