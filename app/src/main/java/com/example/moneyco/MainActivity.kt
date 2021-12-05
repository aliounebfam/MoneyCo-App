package com.example.moneyco

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneyco.navigation.nav_graph.SetupNavGraph
import com.example.moneyco.ui.theme.MoneyCoTheme


@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MoneyCoTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }


    }
}
