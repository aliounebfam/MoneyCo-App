package com.example.moneyco.navigation


const val ROOT_ROUTE = "root"
const val AUTH_ROUTE = "auth"
const val MAIN_ROUTE = "home"
const val BOTTOM_ROUTE = "bottom"


const val LOGIN_PHONE_NUMBER = "phone"

sealed class Screen(val route: String) {
    object LogIn : Screen(route = "logIn_screen")
    object SignUp : Screen(route = "signUp_screen")
    object MainHome : Screen(route = "main_screen")
}
