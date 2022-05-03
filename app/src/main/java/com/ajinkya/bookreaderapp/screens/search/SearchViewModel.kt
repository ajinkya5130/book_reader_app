package com.ajinkya.bookreaderapp.screens.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajinkya.bookreaderapp.data.ApiResponse
import com.ajinkya.bookreaderapp.model.OnlineBookModel
import com.ajinkya.bookreaderapp.repository.BooksRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: BooksRepoInterface) :
    ViewModel() {
    var bookLists = mutableStateOf(OnlineBookModel()) /*MutableState<DataOrException<OnlineBookModel, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(
                OnlineBookModel(), false, Exception("")
            )
        )*/
    //val bookLists = _booksList

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBook("Android")
    }

    /*fun searchBook(bookName: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (bookName.isEmpty()) return@launch

            bookLists.value.loading = true
            bookLists.value = repository.getBooksList(bookName = bookName)
            if (bookLists.value.data.toString().isNotEmpty())
                bookLists.value.loading = false

            Log.e(TAG, "searchBook: ${bookLists.value.data!!.items?.size}")

        }

    }*/

    fun searchBook(bookName: String) {
        viewModelScope.launch {
            if (bookName.isEmpty()) return@launch

            when (val response = repository.getBooksList(bookName = bookName)) {
                is ApiResponse.Error -> {
                    Log.e(TAG, "searchBook: ${response.message}")
                }
                is ApiResponse.Loading -> {}
                is ApiResponse.Success -> {
                    bookLists.value = response.data!!
                }
            }

        }

    }

}