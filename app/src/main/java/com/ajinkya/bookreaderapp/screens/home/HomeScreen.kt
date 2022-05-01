package com.ajinkya.bookreaderapp.screens.home

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ajinkya.bookreaderapp.R
import com.ajinkya.bookreaderapp.components.FABContent
import com.ajinkya.bookreaderapp.components.ListBooks
import com.ajinkya.bookreaderapp.components.ReaderAppBar
import com.ajinkya.bookreaderapp.components.TitleSection
import com.ajinkya.bookreaderapp.model.BookModel
import com.ajinkya.bookreaderapp.navigation.ReaderScreensEnum
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "HomeScreen"
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        ReaderAppBar(
            title = stringResource(id = R.string.app_name),
            showProfile = true,
            navController = navController
        )
    }, floatingActionButton = {
        FABContent {
            navController.navigate(ReaderScreensEnum.SearchScreen.name)
        }
    }) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeContent(navController)

        }

    }

}

@Preview
@Composable
fun HomeContent(navController: NavHostController = rememberNavController()) {

    val listOfBooks = BookModel().sampleList()
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUser = if (!email.isNullOrEmpty()) {
        email.split("@")[0]
    } else {
        "N/A"
    }
    Column(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.Top) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            TitleSection(label = "Your Reading")
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {

                        navController.navigate(ReaderScreensEnum.ReaderStatScreen.name)

                    }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Account Icon",
                    tint = MaterialTheme.colors.secondary
                )
                Text(
                    text = currentUser, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.overline,
                    maxLines = 1,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Clip
                )


            }

        }
        CurrentReadingBooksSection(listOf(), navController = navController)
        Spacer(modifier = Modifier.size(10.dp))
        Divider()
        TitleSection(label = "Reading Book List")
        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<BookModel>, navController: NavHostController) {

    HorizontalScrollableCompo(listOfBooks) {
        Log.e(TAG, "BookListArea: $it")
        //

    }
}

@Composable
fun HorizontalScrollableCompo(listOfBooks: List<BookModel>, onCardPressed: (BookModel) -> Unit) {
    val scrollable = rememberScrollState()

    /*LazyRow(modifier = Modifier
        .fillMaxWidth()
        .heightIn(200.dp)){
        items(listOfBooks){ item ->
            ListBooks(item){
                onCarPressed(it)

            }
        }
    }*/

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(200.dp)
            .horizontalScroll(scrollable)
    ) {
        for (book in listOfBooks) {
            ListBooks(book) {
                onCardPressed(it)

            }
        }

    }

}

@Composable
fun CurrentReadingBooksSection(books: List<BookModel>, navController: NavHostController) {

    ListBooks()

}