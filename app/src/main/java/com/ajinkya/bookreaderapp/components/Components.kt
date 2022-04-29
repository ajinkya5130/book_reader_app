package com.ajinkya.bookreaderapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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