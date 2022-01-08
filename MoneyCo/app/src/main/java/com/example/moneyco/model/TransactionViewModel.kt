package com.example.moneyco.model

import com.example.moneyco.data.MesTransactions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


fun enregistrerTransactions(
    transactions: MesTransactions
) {
    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser
    db.collection("users")
        .document(currentUser!!.uid)
        .collection("mesTransactions")
        .add(transactions)
}


fun editerTransaction(
    transactions: MesTransactions,
    docId: String
) {
    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser
    db.collection("users")
        .document(currentUser!!.uid)
        .collection("mesTransactions")
        .document(docId)
        .set(transactions)
//        .add()
}