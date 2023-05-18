package com.example.mangotestwork.core.presentation

sealed class Screens(val route: String) {
    object Login: Screens("login")
    object Registration: Screens("registration")
    object Profile: Screens("profile")
    object EditProfile: Screens("editProfile")
}