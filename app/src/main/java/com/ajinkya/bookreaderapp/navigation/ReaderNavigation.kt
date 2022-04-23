package com.ajinkya.bookreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajinkya.bookreaderapp.screens.details.BookDetailsScreen
import com.ajinkya.bookreaderapp.screens.home.HomeScreen
import com.ajinkya.bookreaderapp.screens.search.ReaderBookSearchScreen
import com.ajinkya.bookreaderapp.screens.splashScreen.ReaderSplashScreen
import com.ajinkya.bookreaderapp.screens.stats.ReaderBookStatScreen
import com.ajinkya.bookreaderapp.screens.updates.ReaderUpdateScreen

@Composable
fun ReaderNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreensEnum.SplashScreen.name) {

        composable(route = ReaderScreensEnum.SplashScreen.name) {
            ReaderSplashScreen(navController)
        }
        composable(route = ReaderScreensEnum.ReaderHomeScreen.name) {
            HomeScreen(navController)
        }
        composable(route = ReaderScreensEnum.DetailsScreen.name) {
            BookDetailsScreen(navController)
        }
        composable(route = ReaderScreensEnum.SearchScreen.name) {
            ReaderBookSearchScreen(navController)
        }
        composable(route = ReaderScreensEnum.UpdateScreen.name) {
            ReaderUpdateScreen(navController)
        }
        composable(route = ReaderScreensEnum.ReaderStatScreen.name) {
            ReaderBookStatScreen(navController)
        }

    }
}