package com.example.moneyco.screens.main.profil

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.moneyco.components.ShimmerInfoProfil
import com.example.moneyco.components.ShimmerProfil
import com.example.moneyco.model.MyViewModel
import com.example.moneyco.screens.main.profil.components.EditableTextField
import com.example.moneyco.screens.main.profil.components.LottieFiles
import com.example.moneyco.ui.theme.Merienda
import com.example.moneyco.ui.theme.Nunito
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@DelicateCoroutinesApi
@ExperimentalCoilApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfilScreen() {
    var name by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    var visible by remember { mutableStateOf(true) }
    var boolean by remember { mutableStateOf(false) }

    val currentUser = Firebase.auth.currentUser
    val db = Firebase.firestore

    val viewModel: MyViewModel = viewModel()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    GlobalScope.launch {
        val doc = db.collection("users").document(currentUser!!.uid)
        doc.get()
            .addOnSuccessListener { document ->
                name = document.data?.get("displayName").toString()
                image = document.data?.get("photoURl").toString()
                email = document.data?.get("email").toString()
                phone = document.data?.get("phoneNumber").toString()
                budget = document.data?.get("budget").toString()
                visible = false
            }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mon profil",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Nunito,
                        fontSize = 30.sp
                    )
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                elevation = 8.dp,
            )
        },
        content = {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { viewModel.refresh() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        contentColor = MaterialTheme.colors.primary,
                        largeIndication = false,
                        elevation = 8.dp
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    val painter = rememberImagePainter(data = image, builder = {
                        transformations(
                            CircleCropTransformation()
                        )
                        crossfade(true)
                    })

                    Spacer(modifier = Modifier.height(22.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val painterState = painter.state
                        if (visible) {
                            ShimmerProfil()
                        } else {
                            if (image != "") {
                                Image(
                                    painter = painter, contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = if (painterState is ImagePainter.State.Success) {
                                        Modifier
                                            .size(110.dp)
                                            .padding(3.dp)
                                            .border(
                                                width = 3.dp,
                                                color = MaterialTheme.colors.primaryVariant.copy(
                                                    alpha = 0.8f
                                                ),
                                                shape = CircleShape
                                            )
                                            .shadow(
                                                elevation = 9.dp,
                                                shape = CircleShape,
                                                clip = true
                                            )
                                    } else {
                                        Modifier
                                            .size(110.dp)
                                            .padding(3.dp)
                                    }
                                )
                            } else {
                                LottieFiles(
                                    modifier = Modifier
                                        .size(110.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF7F7F7))
                                        .border(
                                            width = 3.dp,
                                            color = MaterialTheme.colors.primaryVariant.copy(
                                                alpha = 0.8f
                                            ),
                                            shape = CircleShape
                                        )
                                        .padding(3.dp)
                                )
                            }
                            if (name == "") {
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    fontSize = MaterialTheme.typography.body2.fontSize,
                                    fontStyle = FontStyle.Italic,
                                    text = "Nom et prénom(s) non renseignés",
                                    color = Color.Gray,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            } else {
                                Text(
                                    text = name,
                                    fontSize = MaterialTheme.typography.h5.fontSize,
                                    fontFamily = Merienda
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Informations personnelles",
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontFamily = Nunito

                        )
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    if (visible) {
                        ShimmerInfoProfil()
                    }

                    if (!visible) {

                        EditableTextField(
                            content = name,
                            label = "Nom et prénom(s)",
                            icon = Icons.Rounded.Badge,
                            descriptionIcon = "Badge",
                            field = "displayName"
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        EditableTextField(
                            content = email,
                            label = "Adresse email",
                            icon = Icons.Rounded.AlternateEmail,
                            descriptionIcon = "Email",
                            keyboardType = KeyboardType.Email,
                            field = "email"
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        EditableTextField(
                            content = phone,
                            label = "Numéro de téléphone",
                            field = "phoneNumber",
                            icon = Icons.Rounded.Phone,
                            descriptionIcon = "Téléphone",
                            keyboardType = KeyboardType.Phone
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        EditableTextField(
                            content = budget,
                            label = "Budget actuel",
                            field = "budget",
                            icon = Icons.Rounded.AttachMoney,
                            descriptionIcon = "Money",
                            keyboardType = KeyboardType.Number
                        )
                    }

                    Spacer(modifier = Modifier.height(45.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Button(
                            onClick = {
                                boolean = !boolean
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = 5.dp
                                ),
                            shape = RoundedCornerShape(9.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFEF5350)
                            ),
                            elevation = ButtonDefaults.elevation(
                                5.dp
                            ),
                        ) {
                            Text(
                                text = "Se deconnecter",
                                modifier = Modifier.padding(
                                    top = 5.dp,
                                    bottom = 5.dp,
                                ),
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(55.dp))
                    if (boolean) {
                        com.example.moneyco.screens.main.profil.components.AlertDialog()
                    }
                }
            }
        }
    )

}



