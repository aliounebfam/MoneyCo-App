package com.example.moneyco.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.material.ExperimentalMaterialApi
import com.example.moneyco.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
            currentUser.phoneNumber,
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

@ExperimentalMaterialApi
fun signOut(context: Context) {
    GlobalScope.launch {
        val join = launch {
            Firebase.auth.signOut()
            delay(850L)
        }
        join.join()
        if (Firebase.auth.currentUser?.phoneNumber != null) {
            exitProcess(0)
        } else {
            val activity = (context as? Activity)
            activity?.finish()
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}