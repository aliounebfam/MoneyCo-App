package com.example.moneyco.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RevenuScreen(navController: NavController) {

    val db = Firebase.firestore
    var boolean by remember { mutableStateOf(false) }

    val revenus = remember {
        mutableStateListOf<String>()
    }


    GlobalScope.launch {
        val doc = db.collection("revenus")
        doc.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    revenus.add(document.data["nom"] as String)
                }
                revenus.remove("Autres")
            }
    }

//    if(boolean) {
    LazyColumn(
        contentPadding = PaddingValues(
            all = 15.dp
        ),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
//            items(items = revenus.distinct()) { revenu ->
//                Text(text = revenu, modifier = Modifier.clickable {
//                    navController.navigate(BottomBarScreen.Profil.route)
//                })
//            }

        itemsIndexed(
            items = revenus.distinct()
        ) { index, revenu ->
//                if(index != 1){
            Text(text = revenu)
//                }
        }
        item {
            Text(text = "Autres")
        }
//            itemsIndexed()
    }
//    }
}