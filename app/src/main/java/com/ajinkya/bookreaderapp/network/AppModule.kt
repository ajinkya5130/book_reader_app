package com.ajinkya.bookreaderapp.network

import com.ajinkya.bookreaderapp.repository.BookRepository
import com.ajinkya.bookreaderapp.repository.BooksRepoInterface
import com.ajinkya.bookreaderapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRetrofitInstance(): BooksApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BooksApi::class.java)


    @Provides
    @Singleton
    fun provideBooksApi(api: BooksApi) =
        BookRepository(api) as BooksRepoInterface

}