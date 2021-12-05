package com.example.moneyco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneyco.navigation.nav_graph.SetupNavGraph
import com.example.moneyco.screens.SplashScreen
import com.example.moneyco.screens.SplashScreenView
import com.example.moneyco.ui.theme.MoneyCoTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyCoTheme {
                navController =  rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
