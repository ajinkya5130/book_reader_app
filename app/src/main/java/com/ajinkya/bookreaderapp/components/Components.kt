package com.ajinkya.bookreaderapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.ajinkya.bookreaderapp.model.BookModel
import com.ajinkya.bookreaderapp.navigation.ReaderScreensEnum
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(bottom = 10.dp),
        text = "Book Reader",
        style = MaterialTheme.typography.h4,
        color = Color.Red.copy(.4f)
    )
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    lableId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = lableId,
        enabled = enabled,
        imeAction = imeAction,
        onAction = onAction,
        keyboardType = KeyboardType.Email
    )

}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value, onValueChange = {
            valueState.value = it
        },
        label = {
            Text(text = labelId)
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction)
    )

}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    onAction: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Done
) {

    val visualTransformation = if (passwordVisibility.value) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    OutlinedTextField(
        value = passwordState.value, onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {
            PasswordVisibility(passwordVisibility = passwordVisibility)
        },
        keyboardActions = onAction
    )

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {

    val visibility = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visibility }) {

        Icons.Default.Close

    }
}

@Preview
@Composable
fun ReaderAppBar(
    title: String = "AppBar",
    showProfile: Boolean = true,
    navController: NavHostController = rememberNavController()
) {
    TopAppBar(modifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp)),
        backgroundColor = Color.Transparent,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(5.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            ) {
                if (showProfile) {
                    Icon(
                        imageVector = Icons.Filled.Animation, contentDescription = "Icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(1f)
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = title,
                    color = Color.Red.copy(0.5f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(130.dp))


            }
        },
        elevation = 4.dp,
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(ReaderScreensEnum.LoginScreen.name)
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout",
                    tint = Color.Red
                )
            }
        }
    )
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


@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .height(80.dp),
        shape = RoundedCornerShape(60.dp),
        elevation = 50.dp,
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Rounded.StarBorder, contentDescription = "Rating",
                modifier = Modifier.padding(3.dp)
            )
            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1)

        }

    }

}


@Composable
fun RoundedButtonShape(
    label: String = "Reading",
    radius: Int = 30,
    onPress: () -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(
            bottomEndPercent = radius,
            topStartPercent = radius
        ), color = MaterialTheme.colors.secondaryVariant
    ) {
        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(70.dp)
            .clickable {
                onPress.invoke()
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Text(text = label, fontSize = 15.dp, color = Color.Blue)
            Text(text = label, fontSize = 15.sp, color = Color.White)

        }

    }
}


@Composable
fun ListBooks(
    book: BookModel = BookModel("1", "Ajinkya", "sample author", "Sample note"),
    onPressDetails: (BookModel) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(
        modifier = Modifier
            .padding(5.dp)
            .height(230.dp)
            .width(280.dp)
            .fillMaxWidth()
            .clickable {
                onPressDetails.invoke(book)
            }/*.clip(RoundedCornerShape(25.dp))*/, shape = RoundedCornerShape(25.dp),
        elevation = 6.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberImagePainter(data = ""), contentDescription = "Book Cover",
                    modifier = Modifier
                        .width(150.dp)
                        .height(140.dp)
                )

                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Favorite",
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    BookRating(score = 3.5)


                }
            }

            Text(
                text = book.name!!, modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.authors!!, modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.subtitle2
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
            ) {
                RoundedButtonShape("Reading", 70)
            }

        }


    }

}