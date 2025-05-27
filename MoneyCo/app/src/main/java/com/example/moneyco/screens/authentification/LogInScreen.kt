package com.example.moneyco.screens.authentification

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneyco.R
import com.example.moneyco.model.AuthViewModel
import com.example.moneyco.navigation.AUTH_ROUTE
import com.example.moneyco.navigation.MAIN_ROUTE
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.authentification.components.DividerLogin
import com.example.moneyco.screens.authentification.components.SignGoogleButton
import com.example.moneyco.screens.authentification.components.TextAlreadyAccount
import com.example.moneyco.ui.theme.Merienda
import com.example.moneyco.ui.theme.surface_variant
import com.example.moneyco.utils.LoadingState
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authState by authViewModel.authState.collectAsState()
    val scope = rememberCoroutineScope()
    
    var phoneNumber by remember { mutableStateOf("") }

    // Check if user is already logged in
    LaunchedEffect(key1 = true) {
        if (authViewModel.isUserLoggedIn()) {
            navController.navigate(MAIN_ROUTE) {
                popUpTo(AUTH_ROUTE) { inclusive = true }
            }
        }
    }

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
        // App Logo section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight(0.22f)
                .padding(top = 30.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.moneyco_icon),
                contentDescription = "logo MoneyCo",
                modifier = Modifier.weight(2.7f)
            )
            Text(
                text = "MoneyCo",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 25.sp,
                fontFamily = Merienda,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
        }

        // Login card
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
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            ),
            elevation = 8.dp
        ) {
            val localFocusManager = LocalFocusManager.current
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(22.dp))

                // Title section
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
                        modifier = Modifier.fillMaxWidth(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                // Phone number field
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(text = "Numéro de téléphone") },
                    placeholder = { Text(text = "") },
                    leadingIcon = { Icon(Icons.Filled.Phone, "phone") },
                    trailingIcon = {
                        if (phoneNumber.isNotEmpty()) {
                            Icon(
                                Icons.Filled.Clear,
                                "effacer",
                                modifier = Modifier.clickable {
                                    phoneNumber = ""
                                }
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus()
                            if (phoneNumber.isNotEmpty()) {
                                authViewModel.signInWithPhone(phoneNumber)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Veuillez entrer un numéro de téléphone",
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

                // Login button
                Button(
                    onClick = {
                        if (phoneNumber.isNotEmpty()) {
                            authViewModel.signInWithPhone(phoneNumber)
                        } else {
                            Toast.makeText(
                                context,
                                "Veuillez entrer un numéro de téléphone",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        disabledBackgroundColor = surface_variant
                    ),
                    elevation = ButtonDefaults.elevation(8.dp),
                    enabled = (phoneNumber.length > 1)
                ) {
                    if (authState == LoadingState.LOADING) {
                        CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                    } else {
                        Text(
                            text = "Obtenir code de vérification",
                            modifier = Modifier.padding(
                                top = 5.dp,
                                bottom = 5.dp,
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                
                DividerLogin()
                
                Spacer(modifier = Modifier.height(18.dp))
                
                // Google sign-in button
                SignGoogleButton(
                    text = "Se connecter avec Google",
                    onClicked = {
                        // In SQLite implementation, we're using a simplified version
                        authViewModel.signInWithGoogle("Test User", "test@example.com", "")
                    }
                )
                
                Spacer(modifier = Modifier.height(43.dp))
                
                // Register link
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

    // Handle authentication state
    LaunchedEffect(key1 = authState) {
        when (authState) {
            LoadingState.LOADED -> {
                navController.navigate(MAIN_ROUTE) {
                    popUpTo(AUTH_ROUTE) { inclusive = true }
                }
            }
            is LoadingState.ERROR -> {
                Toast.makeText(
                    context,
                    (authState as LoadingState.ERROR).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }
}