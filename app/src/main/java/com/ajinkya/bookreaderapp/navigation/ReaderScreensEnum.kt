package com.ajinkya.bookreaderapp.navigation

enum class ReaderScreensEnum {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailsScreen,
    UpdateScreen,
    ReaderStatScreen;

    companion object {
        fun fromRoute(name: String?): ReaderScreensEnum =
            when (name?.substringBefore("/")) {
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                CreateAccountScreen.name -> CreateAccountScreen
                ReaderHomeScreen.name -> ReaderHomeScreen
                SearchScreen.name -> SearchScreen
                DetailsScreen.name -> DetailsScreen
                UpdateScreen.name -> UpdateScreen
                ReaderStatScreen.name -> ReaderStatScreen
                null -> ReaderHomeScreen
                else -> throw IllegalArgumentException("$name is not Configured/Recognized!")
            }

    }

}