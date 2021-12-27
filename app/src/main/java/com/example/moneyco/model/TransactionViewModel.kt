package com.example.moneyco.model

import com.example.moneyco.data.MesRevenus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


fun enregistrerMesRevenus(
    revenus: MesRevenus
) {
    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser
    db.collection("users")
        .document(currentUser!!.uid)
        .collection("mesRevenus")
        .add(revenus)
}