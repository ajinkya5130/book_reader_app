package com.ajinkya.bookreaderapp.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ajinkya.bookreaderapp.R
import com.ajinkya.bookreaderapp.components.ReaderAppBar

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

        }

    }

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
