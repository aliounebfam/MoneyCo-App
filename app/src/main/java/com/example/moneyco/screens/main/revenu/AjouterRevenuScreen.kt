package com.example.moneyco.screens.main.revenu

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moneyco.components.TopAppBarWithBack
import com.example.moneyco.data.MesTransactions
import com.example.moneyco.model.enregistrerTransactions
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.navigation.Screen
import com.example.moneyco.ui.theme.noir
import com.example.moneyco.ui.theme.surface_variant
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore

@ExperimentalComposeUiApi
@Composable
fun AjouterRevenuScreen(
    navController: NavController,
    categorie: String,
    sousCategorie: String
) {

    var montant by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val (focusRequester) = FocusRequester.createRefs()
    val context = LocalContext.current
    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser
    val docRef = db.collection("users").document(currentUser!!.uid)
    var getBudget by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf(0) }
    JetFirestore(
        path = {
            collection("users")
                .document(currentUser.uid)
        },
        onRealtimeDocumentFetch = { value, _ ->
            getBudget = value?.get("budget").toString()
            budget = getBudget.toInt()
            Log.d("testcase", budget.toString())
        }
    ) {}



    Scaffold(
        topBar = {
            TopAppBarWithBack(text = "Ajouter revenu", onBackClick = {
                navController.popBackStack()
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Catégorie: $categorie",
                fontSize = 18.sp,
                color = noir,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(surface_variant.copy(alpha = 0.2f))
                    .padding(
                        bottom = 7.dp,
                        start = 9.dp,
                        top = 7.dp,
                        end = 9.dp
                    )
            )

            if (sousCategorie != " ") {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Sous catégorie: $sousCategorie",
                    fontSize = 16.5.sp,
                    color = noir,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(surface_variant.copy(alpha = 0.2f))
                        .padding(7.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = montant,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { montant = it },
                label = { Text(text = "Entrez le montant") },
                leadingIcon = { Icon(Icons.Filled.AttachMoney, "montant") },
                trailingIcon = {
                    if (montant.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Clear,
                            "effacer",
                            modifier = Modifier.clickable {
                                montant = ""
                            }
                        )
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequester.requestFocus()
                    }
                ),
                shape = RoundedCornerShape(9.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                onValueChange = { description = it },
                label = { Text(text = "Entrez une description") },
                leadingIcon = { Icon(Icons.Filled.Description, "Description") },
                trailingIcon = {
                    if (description.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Clear,
                            "effacer",
                            modifier = Modifier.clickable {
                                description = ""
                            }
                        )
                    }
                },
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                ),
                shape = RoundedCornerShape(9.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    try {
                        enregistrerTransactions(
                            MesTransactions(
                                categorie,
                                sousCategorie,
                                description,
                                montant.toInt(),
                                "revenu",
                                Timestamp.now()
                            )
                        )
                        budget += montant.toInt()
                        docRef.update("budget", budget.toString())
                        navController.navigate(BottomBarScreen.Home.route) {
                            popUpTo(Screen.AjouterRevenu.route) {
                                inclusive = true
                            }
                            popUpTo(Screen.CategorieRevenu.route) {
                                inclusive = true
                            }
                            popUpTo(BottomBarScreen.Home.route) {
                                inclusive = true
                            }
                        }
                        Toast.makeText(
                            context,
                            "Revenu ajouté avec succès \u2713",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "\" $montant \" n'est pas un nombre entier",
                            Toast.LENGTH_LONG
                        ).show()
                        montant = ""
                    }

                },
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    disabledBackgroundColor = surface_variant
                ),
                elevation = ButtonDefaults.elevation(
                    8.dp
                ),
                enabled = (montant.isNotEmpty() && description.isNotEmpty())

            ) {
                Text(
                    text = "Enregistrer revenu",
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 5.dp,
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}