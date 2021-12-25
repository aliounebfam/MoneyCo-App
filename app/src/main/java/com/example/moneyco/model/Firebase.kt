package com.example.moneyco.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
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
            activity?.finish()
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}

// fun getDataUser(): MutableMap<String, Any> {
//     val result = mutableMapOf<String, Any>()
//      GlobalScope.launch {
//
//         val currentUser = Firebase.auth.currentUser
//         val db = Firebase.firestore
//         val doc = db.collection("users").document(currentUser!!.uid)
//          doc.get()
//             .addOnSuccessListener { document->
//
//                 document.data?.forEach{
//                         (key, value) -> result[key] = value
//                 }
//             }.await()
////
//
//     }
//     Log.d("testons", result.values.toString())
//     return result
//
//
//
//
//
//
////     currentUser?.let { db.collection("users").document(it.uid) }?.get()
////        ?.addOnSuccessListener { document ->
////            document.data
////        }
//}