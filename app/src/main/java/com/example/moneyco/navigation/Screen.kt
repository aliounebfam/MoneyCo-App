package com.example.moneyco.navigation

const val ROOT_ROUTE = "root"
const val AUTH_ROUTE = "auth"
const val HOME_ROUTE = "home"

sealed class Screen(val route: String) {
    object LogIn : Screen(route = "logIn_screen")
    object SignUp : Screen(route = "signUp_screen")
    object Home : Screen(route = "home_screen")
}
