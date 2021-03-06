package com.ajinkya.bookreaderapp.screens.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ajinkya.bookreaderapp.components.EmailInput
import com.ajinkya.bookreaderapp.components.PasswordInput
import com.ajinkya.bookreaderapp.components.ReaderLogo
import com.ajinkya.bookreaderapp.navigation.ReaderScreensEnum
import com.ajinkya.bookreaderapp.utils.Constants
import com.ajinkya.bookreaderapp.utils.ToastEnum
import com.ajinkya.bookreaderapp.utils.toastMessage


private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val messagePair by viewModel.toastMessage.observeAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            if (showLoginForm.value) {
                UserForm(isCreateAccount = false, loading = false) { email, pass ->
                    Log.e(TAG, "LoginScreen: $email and $pass")
                    //Todo: open Firebase account
                    viewModel.signInWithEmailAndPass(email, pass) {
                        if (it) {
                            navController.navigate(ReaderScreensEnum.ReaderHomeScreen.name)
                        }
                        showToastMessage(messagePair, context)
                    }
                }
            } else {
                UserForm(isCreateAccount = true, loading = false) { email, pass ->
                    //Todo: create Firebase account
                    viewModel.createUserWithEmail(email, pass) {
                        if (it) {
                            navController.navigate(ReaderScreensEnum.ReaderHomeScreen.name)
                        }
                        showToastMessage(messagePair, context)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val text = if (showLoginForm.value) Constants.SIGN_UP else Constants.LOG_IN
            Text(text = Constants.NEW_USER)
            Text(text,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    }
                    .padding(start = 5.dp), fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondaryVariant
            )


        }

    }

}

fun showToastMessage(messagePair: Pair<String, ToastEnum>?, context: Context) {

    toastMessage(message = messagePair!!.first, context = context, duration = messagePair!!.second)
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { emil, pass -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(350.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        /*if (isCreateAccount) {
            Text(
                text = "Please enter a valid email and password that is at least 6 characters",
                modifier.padding(4.dp)
            )
        } else {
            Text(text = "asas")
        }*/

        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })
        PasswordInput(modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisible,
            onAction = KeyboardActions {

                if (!isValid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())

            })


        SubmitButton(
            if (isCreateAccount) Constants.CREATE_ACCOUNT else Constants.LOG_IN,
            loading,
            isValid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
        if (isCreateAccount) {
            Text(
                text = "Please enter a valid email and password that is at least 6 characters",
                modifier.padding(10.dp)
            )
        }


    }

}

@Composable
fun SubmitButton(
    textId: String, loading: Boolean, valid: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(.5f),
        enabled = !loading && valid,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp)) else Text(
            text = textId,
            modifier = Modifier.padding(5.dp)
        )

    }

}
