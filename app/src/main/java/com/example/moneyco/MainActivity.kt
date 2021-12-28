package com.example.moneyco

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.navigation.nav_graph.SetupNavGraph
import com.example.moneyco.ui.theme.MoneyCoTheme
import kotlinx.coroutines.DelicateCoroutinesApi


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@DelicateCoroutinesApi
@ExperimentalCoilApi

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.M)
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



