package com.example.moneyco.screens.main.transaction

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneyco.components.TopAppBarWithBack
import com.example.moneyco.data.MesTransactions
import com.example.moneyco.model.editerTransaction
import com.example.moneyco.ui.theme.surface_variant
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore

@ExperimentalComposeUiApi
@Composable
fun EditTransactionScreen(navController: NavController, idDoc: String) {

    val currentUser = Firebase.auth.currentUser
    var description by remember { mutableStateOf("") }
    var montant by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var categorie by remember { mutableStateOf("") }
    var sous_categorie by remember { mutableStateOf("") }

    val (focusRequester) = FocusRequester.createRefs()

    Scaffold(
        topBar = {
            TopAppBarWithBack(text = "Editer transaction", onBackClick = {
                navController.popBackStack()
            })
        }
    ) {
        JetFirestore(
            path = {
                collection("users")
                    .document(currentUser?.uid.toString())
                    .collection("mesTransactions")
                    .document(idDoc)
            },
            onRealtimeDocumentFetch = { value, _ ->
                description = value?.get("description").toString()
                montant = value?.get("montant").toString()
                type = value?.get("type").toString()
                categorie = value?.get("categorie").toString()
                sous_categorie = value?.get("sous_categorie").toString()
            }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                OutlinedTextField(
                    value = montant,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { montant = it },
                    label = { Text(text = "Montant") },
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
                    label = { Text(text = "Description") },
                    leadingIcon = { Icon(Icons.Filled.Description, "description") },
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
                        editerTransaction(
                            docId = idDoc,
                            transactions = MesTransactions(
                                categorie,
                                sous_categorie,
                                description,
                                montant = montant.toInt(),
                                type,
                                Timestamp.now()
                            )
                        )
                        navController.popBackStack()
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
                        text = "Appliquer les changements",
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
}