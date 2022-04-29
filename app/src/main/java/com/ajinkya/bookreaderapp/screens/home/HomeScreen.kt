package com.ajinkya.bookreaderapp.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ajinkya.bookreaderapp.R
import com.ajinkya.bookreaderapp.components.ReaderAppBar
import com.ajinkya.bookreaderapp.model.BookModel
import com.ajinkya.bookreaderapp.navigation.ReaderScreensEnum
import com.google.firebase.auth.FirebaseAuth

@Preview
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

@Composable
fun HomeContent(navController: NavHostController) {

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

    }
}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Surface(modifier = modifier.padding(5.dp)) {
        Column {
            Text(
                modifier = modifier,
                text = label,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Left
            )
        }

    }
}

@Composable
fun CurrentReadingBooksSection(books: List<BookModel>, navController: NavHostController) {

}


@Composable
fun FABContent(onTap: (String) -> Unit = {}) {

    FloatingActionButton(
        onClick = { onTap("") }, shape = RoundedCornerShape(50.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "Fab button",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )

    }

}
