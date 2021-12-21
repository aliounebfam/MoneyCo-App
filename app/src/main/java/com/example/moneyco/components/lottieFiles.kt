package com.example.moneyco.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.moneyco.R

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun lottieFiles(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val customView = remember { LottieAnimationView(context) }
    AndroidView(
        {
            customView
        },
        modifier = modifier
    ) { view ->
        with(view) {
            setAnimation(R.raw.avatar_lottie)
            playAnimation()
            repeatCount = LottieDrawable.INFINITE
        }
    }

}