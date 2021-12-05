package com.example.moneyco.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneyco.R
import com.example.moneyco.navigation.HOME_ROUTE
import com.example.moneyco.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(
                durationMillis = 600,
                easing = {
                    android.view.animation.OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        navController.navigate(HOME_ROUTE) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.moneyco_icon),
            contentDescription = R.string.icon_moneyco.toString(),
            modifier = Modifier
                .fillMaxSize()
                .padding(95.dp)
                .scale(scale.value)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenView() {
    SplashScreen(navController = rememberNavController())
}