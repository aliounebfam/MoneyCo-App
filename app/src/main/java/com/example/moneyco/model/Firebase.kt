package com.example.moneyco.model

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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