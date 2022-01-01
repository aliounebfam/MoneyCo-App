package com.example.moneyco.screens.authentification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.OtpLoginActivity
import com.example.moneyco.R
import com.example.moneyco.model.AuthViewModel
import com.example.moneyco.navigation.AUTH_ROUTE
import com.example.moneyco.navigation.MAIN_ROUTE
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.authentification.components.DividerLogin
import com.example.moneyco.screens.authentification.components.SignGoogleButton
import com.example.moneyco.screens.authentification.components.TextAlreadyAccount
import com.example.moneyco.ui.theme.Merienda
import com.example.moneyco.utils.LoadingState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.coroutines.DelicateCoroutinesApi

const val NUMBER_PHONE = ""

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@DelicateCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun LogInScreen(
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {

    val state by viewModel.loadingState.collectAsState()
    val auth: FirebaseAuth = Firebase.auth
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                viewModel.signWithCredential(credential)
            } catch (e: ApiException) {
                Log.w("TAG", "Echec Google Sign In", e)
            }
        }
    val context = LocalContext.current
    val token = "612915712773-9jmoegn294nhuvvskfvfro5cunirpldu.apps.googleusercontent.com"
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(token)
        .requestEmail()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    val telephonyManager =
        LocalContext.current.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.createInstance(LocalContext.current)
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.primary,
                    ),
                    tileMode = TileMode.Mirror,
                )
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight(0.22f)
                .padding(
                    top = 30.dp
                )
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.moneyco_icon),
                contentDescription = "logo MoneyCo",
                modifier = Modifier
                    .weight(2.7f)
            )
            Text(
                text = "MoneyCo",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 25.sp,
                fontFamily = Merienda,
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
            )

        }

        Card(
            modifier = Modifier
                .padding(
                    top = 0.dp,
                    start = 2.dp,
                    end = 2.dp,
                    bottom = 0.2.dp
                )
                .fillMaxHeight(0.88f)
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState()
                ),
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            ),
            elevation = 8.dp
        )
        {
            val localFocusManager = LocalFocusManager.current
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,


                ) {

                Spacer(modifier = Modifier.height(22.dp))

                Column {
                    Text(
                        text = ("Ravie de vous revoir !"),
                        fontFamily = Merienda,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D2424)
                        ),
                        fontSize = 23.5.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(1f)

                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Connectez vous à votre compte existant",
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(text = "Numéro de téléphone") },
                    placeholder = { Text(text = "") },
                    leadingIcon = { Icon(Icons.Filled.Phone, "phone") },
                    trailingIcon = {
                        if (phoneNumber.isNotEmpty()) {
                            Icon(Icons.Filled.Clear, "effacer", modifier = Modifier.clickable {
                                phoneNumber = ""
                            })
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            try {
                                val phone =
                                    phoneUtil.parse(
                                        phoneNumber,
                                        telephonyManager.simCountryIso.uppercase()
                                    )
                                val isValid = phoneUtil.isValidNumber(phone)
                                if (isValid) {
                                    localFocusManager.clearFocus()
                                    val activity = (context as? Activity)
                                    activity?.finish()
                                    context.startActivity(
                                        Intent(
                                            context,
                                            OtpLoginActivity::class.java
                                        ).apply {
                                            putExtra(
                                                NUMBER_PHONE,
                                                "+${phone.countryCode}${phone.nationalNumber}"
                                            )
                                        })
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Le numéro +${phone.countryCode}${phone.nationalNumber} est invalide",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } catch (e: Exception) {
                                Toast.makeText(
                                    context, "Le numéro de téléphone saisi est invalide",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ),

                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        leadingIconColor = MaterialTheme.colors.secondaryVariant
                    ),
                    shape = RoundedCornerShape(9.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        try {
                            val phone =
                                phoneUtil.parse(
                                    phoneNumber,
                                    telephonyManager.simCountryIso.uppercase()
                                )
                            val isValid = phoneUtil.isValidNumber(phone)
                            if (isValid) {
                                val activity = (context as? Activity)
                                activity?.finish()
                                context.startActivity(
                                    Intent(
                                        context,
                                        OtpLoginActivity::class.java
                                    ).apply {
                                        putExtra(
                                            NUMBER_PHONE,
                                            "+${phone.countryCode}${phone.nationalNumber}"
                                        )
                                    })
                            } else {
                                Toast.makeText(
                                    context,
                                    "Le numéro +${phone.countryCode}${phone.nationalNumber} est invalide",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } catch (e: Exception) {
                            Toast.makeText(
                                context, "Le numéro de téléphone saisi est invalide",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        disabledBackgroundColor = Color(0xFFCCCFDA)
                    ),
                    elevation = ButtonDefaults.elevation(
                        8.dp
                    ),
                    enabled = (phoneNumber.length > 1)
                ) {
                    Text(
                        text = "Obtenir code de vérification",
                        modifier = Modifier.padding(
                            top = 5.dp,
                            bottom = 5.dp,
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
                DividerLogin()
                Spacer(modifier = Modifier.height(18.dp))
                SignGoogleButton(
                    text = "Se connecter avec Google",
                    onClicked = {
                        if (auth.currentUser?.displayName == null) {
                            googleSignInClient.signOut()
                            googleSignInClient.revokeAccess()
                            launcher.launch(googleSignInClient.signInIntent)
                            if (state.status == LoadingState.Status.FAILED) {
                                Toast.makeText(
                                    context,
                                    state.msg ?: "Error",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                        if (auth.currentUser?.displayName != null) {
                            when (state.status) {
                                LoadingState.Status.SUCCESS -> {

                                    navController.navigate(MAIN_ROUTE) {
                                        popUpTo(AUTH_ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                }
                                LoadingState.Status.FAILED -> {
                                    Toast.makeText(
                                        context,
                                        state.msg ?: "Error",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                else -> {
                                }
                            }
                        }
                        when (state.status) {
                            LoadingState.Status.FAILED -> {
                                Toast.makeText(
                                    context,
                                    state.msg ?: "Error",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            else -> {

                            }
                        }
                    },
                )
                Spacer(modifier = Modifier.height(43.dp))
                TextAlreadyAccount(
                    onClick = {
                        navController.navigate(Screen.SignUp.route) {
                            popUpTo(Screen.LogIn.route) {
                                inclusive = true
                            }
                        }
                    },
                    text1 = "Pas encore de compte ? ",
                    text2 = "INSCRIVEZ VOUS",
                    fontSize = 13.sp
                )
            }

        }

    }
}


