package com.example.moneyco.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneyco.navigation.AUTH_ROUTE
import com.example.moneyco.navigation.MAIN_ROUTE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(navController: NavController) {

    val currentUser = Firebase.auth.currentUser

    var displayName by remember {
        mutableStateOf("")
    }

    val db = Firebase.firestore

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(45.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val doc = currentUser?.let { db.collection("users").document(it.uid) }
        doc?.get()?.addOnSuccessListener { document ->
            if (document != null) {
                displayName = document.data?.get("displayName").toString()
                Log.d("document", "DocumentSnapshot data: ${document.data?.get("phoneNumber")}")
            } else {
                Log.d("document", "No such document")
            }
        }

        Text(
            text = displayName,
            style = TextStyle(
                fontWeight = FontWeight(25),
                fontSize = MaterialTheme.typography.h3.fontSize
            )
        )

        Button(onClick = {
            Firebase.auth.signOut()
            navController.navigate(AUTH_ROUTE) {
                popUpTo(MAIN_ROUTE) {
                    inclusive = true
                }
            }

        }) {
            Text(text = "Sign OUT")

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}