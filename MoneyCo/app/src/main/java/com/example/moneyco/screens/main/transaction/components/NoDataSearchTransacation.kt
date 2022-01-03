package com.example.moneyco.screens.main.transaction.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.moneyco.R

@Composable
fun NoDataLottieFiles(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val customView = remember { LottieAnimationView(context) }
    AndroidView(
        {
            customView
        },
        modifier = modifier
    ) { view ->
        with(view) {
            setAnimation(R.raw.no_data_lottie)
            playAnimation()
            repeatCount = LottieDrawable.INFINITE
        }
    }
}