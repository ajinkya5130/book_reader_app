package com.ajinkya.bookreaderapp.screens.search

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ajinkya.bookreaderapp.components.InputField
import com.ajinkya.bookreaderapp.components.ReaderAppBar
import com.ajinkya.bookreaderapp.model.BookModel

private const val TAG = "ReaderSearchBookScreen"

@Preview
@Composable
fun ReaderBookSearchScreen(navController: NavHostController = NavHostController(LocalContext.current)) {
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
        androidx.compose.material.Surface {
            Column {

                SearchBook(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Log.e(TAG, "ReaderBookSearchScreen: $it")
                }
                BookList(navController)

            }
        }

    }
}

@Composable
fun BookList(navController: NavHostController) {
    LazyColumn {
        items(BookModel().sampleList()) { item ->
            BookInfo(item)
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
fun BookInfo(item: BookModel) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 150.dp)
            .clickable { },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, color = Color.DarkGray),
        elevation = 10.dp
    ) {

        Row {
            Icon(
                imageVector = Icons.Default.Download, contentDescription = "", modifier =
                Modifier
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
                    text = "Book name", fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Book name", fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.Serif, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Book name", fontWeight = FontWeight.Thin,
                    fontFamily = FontFamily.Serif, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Book name", fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif, overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}