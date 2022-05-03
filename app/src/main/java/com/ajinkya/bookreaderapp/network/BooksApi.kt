package com.ajinkya.bookreaderapp.network

import com.ajinkya.bookreaderapp.model.Item
import com.ajinkya.bookreaderapp.model.OnlineBookModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BooksApi {

    @GET("volumes")
    suspend fun getBooks(@Query("q") bookName: String): Response<OnlineBookModel>


    //https://www.googleapis.com/books/v1/volumes/Wu5qDwAAQBAJ    "Wu5qDwAAQBAJ" <---- BookID
    @GET("volumes/{bookId}")
    suspend fun getPerticularBook(@Path("bookId") bookId: String): Response<Item>

}