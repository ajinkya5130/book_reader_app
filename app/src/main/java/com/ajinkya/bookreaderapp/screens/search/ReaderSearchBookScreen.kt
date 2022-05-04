package com.ajinkya.bookreaderapp.screens.search

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.ajinkya.bookreaderapp.components.InputField
import com.ajinkya.bookreaderapp.components.ReaderAppBar
import com.ajinkya.bookreaderapp.model.Item

private const val TAG = "ReaderSearchBookScreen"

@Preview
@Composable
fun ReaderBookSearchScreen(
    navController: NavHostController = NavHostController(LocalContext.current),
    viewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Book",
            showProfile = false,
            navController = navController,
            iconDrawable = Icons.Default.ArrowBack,
            onBackPressed = {
                navController.popBackStack()
//                navController.navigate(ReaderScreensEnum.ReaderHomeScreen.name)
            })
    }) {
        Surface {
            Column {

                SearchBook(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    viewModel.searchBook(it)
                    Log.e(TAG, "ReaderBookSearchScreen: $it")
                }
                BookList(navController, viewModel)

            }
        }

    }
}

@Composable
fun BookList(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    val list = viewModel.bookLists.value
    val isLoading = viewModel.isLoading
    if (isLoading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    } else {

        Log.e(TAG, "BookList: $list")
        LazyColumn {
            items(list.items!!) { item ->
                BookInfo(item, navController)
            }
        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBook(
    modifier: Modifier? = Modifier,
    loading: Boolean = false,
    hint: String = "Enter book title to search...",
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQuery = rememberSaveable() {
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQuery.value) {
            searchQuery.value.trim().isNotEmpty()
        }
        InputField(valueState = searchQuery, labelId = "Search", enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQuery.value.trim())
                searchQuery.value = ""
                keyboardController?.hide()
            }
        )


    }

}

@Composable
fun BookInfo(item: Item, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 170.dp)
            .clickable { },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, color = Color.DarkGray),
        elevation = 10.dp
    ) {
        var imageURL: String = ""
        var authors: String = ""
        var publisher: String = ""
        var category: String = ""
        Row {


            try {
                imageURL = item.volumeInfo.imageLinks.smallThumbnail
                val sbForAuthors = StringBuilder()
                val sbForCategory = StringBuilder()
                item.volumeInfo.authors.forEach { sbForAuthors.append(it).append(",") }
                authors = sbForAuthors.toString()
                publisher = item.volumeInfo.publisher
                item.volumeInfo.categories.forEach { sbForCategory.append(it).append(",") }
                category = sbForCategory.deleteAt(sbForCategory.length - 1).toString()
            } catch (e: Exception) {
                Log.e(TAG, "BookInfo: ${e.message}")
            }

            val painter =
                rememberImagePainter(data = imageURL)

            Image(
                contentScale = ContentScale.FillHeight,
                painter = painter,
                contentDescription = "Forest Image",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Book name - ${item.volumeInfo.title}",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Category - $category", fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.Serif, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Authors - $authors", fontWeight = FontWeight.Thin,
                    fontFamily = FontFamily.Serif, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Publisher - $publisher", fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif, overflow = TextOverflow.Ellipsis
                )
            }


        }
    }
}