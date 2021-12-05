package com.example.moneyco.navigation

const val ROOT_ROUTE = "root"
const val HOME_ROUTE = "home"
const val AUTH_ROUTE = "auth"

sealed class Screen(val route : String){
    object Splash : Screen(route = "splash_screen")
    object Home : Screen(route = "home_screen")
}
