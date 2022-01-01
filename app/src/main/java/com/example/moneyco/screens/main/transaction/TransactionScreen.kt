package com.example.moneyco.screens.main.transaction

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.moneyco.components.TopAppBarSimple
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("SimpleDateFormat")
@Composable
fun TransactionScreen() {

    Scaffold(topBar = {
        TopAppBarSimple(text = "Transaction")
    }) {
        var categorie: String = ""
        var sousCategorie: String = ""
        var description: String = ""
        var montant: String = ""
        var date: String = ""


        JetFirestore(
            path = {
                collection("users")
                    .document(Firebase.auth.currentUser?.uid.toString())
                    .collection("mesRevenus")
            },
            onRealtimeCollectionFetch = { value, _ ->
                if (value != null) {
                    for (document in value.documents) {
                        montant = document["montant"].toString()
                        description = document["description"].toString()
                        categorie = document["categorie"].toString()
                        sousCategorie = document["sousCategorie"].toString()
                        val sdf = SimpleDateFormat("EEEE dd MMMM yyyy à HH:mm:ss", Locale.FRANCE)
                        val getDate = sdf.format(document.getDate("date"))
                        val affichageDate = getDate.split(' ')
                        date = affichageDate.joinToString(separator = " ") { word ->
                            word.replaceFirstChar {
                                if (it != 'à') {
                                    it.uppercaseChar()
                                } else {
                                    it
                                }
                            }
                        }
                    }
                }
            }
        ) {

        }
    }

}
