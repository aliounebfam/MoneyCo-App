package com.example.moneyco.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.MainActivity
import com.example.moneyco.data.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

fun setupGoogle() {
    val currentUser = Firebase.auth.currentUser
    val db = Firebase.firestore
    val user = currentUser?.displayName?.let {
        User(
            it,
            currentUser.email,
            0,
            "",
            currentUser.photoUrl.toString()
        )
    }
    if (currentUser != null) {
        if (user != null) {
            db.collection("users").document(currentUser.uid)
                .set(user)
        }
    }
}


fun setupOTP(phoneNumber: String) {
    val currentUser = Firebase.auth.currentUser
    val db = Firebase.firestore

    val user = User(
        phoneNumber = phoneNumber
    )

    if (currentUser != null) {
        db.collection("users").document(currentUser.uid)
            .set(user)
    }
}

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@DelicateCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun signOut(context: Context) {
    GlobalScope.launch {
        val join = launch {
            Firebase.auth.signOut()
            delay(800L)
        }
        join.join()

        if (Firebase.auth.currentUser?.phoneNumber != null) {
            exitProcess(0)
        } else {
            val activity = (context as? Activity)
            activity?.finishAffinity()
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
