package com.ajinkya.bookreaderapp.screens.splashScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ajinkya.bookreaderapp.components.ReaderLogo
import com.ajinkya.bookreaderapp.navigation.ReaderScreensEnum
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun ReaderSplashScreen(navController: NavHostController) {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            0.9f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(18f).getInterpolation(it)
            })
        )
        delay(2000L)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(ReaderScreensEnum.LoginScreen.name)
        } else {
            navController.navigate(ReaderScreensEnum.ReaderHomeScreen.name)
        }
        //navController.navigate(ReaderScreensEnum.LoginScreen.name)
    }

    Surface(
        modifier = Modifier
            .padding(10.dp)
            .size(350.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ReaderLogo()

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "\"Read. Change. Yourself\"",
                style = MaterialTheme.typography.h5,
                color = Color.DarkGray.copy(.4f)
            )


        }

    }
}
