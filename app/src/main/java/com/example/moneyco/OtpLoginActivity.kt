package com.example.moneyco

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.screens.OtpScreen
import com.example.moneyco.screens.authentification.NUMBER_PHONE
import com.example.moneyco.ui.theme.MoneyCoTheme
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.concurrent.TimeUnit


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@DelicateCoroutinesApi
@ExperimentalCoilApi
class OtpLoginActivity : AppCompatActivity() {
    lateinit var navController: NavHostController
    private val mAuth = FirebaseAuth.getInstance()
    var verificationOtp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyCoTheme {
                navController = rememberNavController()
                val phone = intent.getStringExtra(NUMBER_PHONE)
                if (phone != null) {
                    Toast.makeText(
                        LocalContext.current,
                        "Je vous prie de patienter...",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    send(phone)
                    OtpScreen { otp ->
                        otpVerification(otp)
                    }
                }
            }
        }

    }


    private fun send(mobileNum: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(mobileNum)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(applicationContext, "Verification completée", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(applicationContext, "Echec vérification", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(otp, p1)
                    verificationOtp = otp
                    Toast.makeText(
                        applicationContext,
                        "Vérifiez votre messagerie",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    @ExperimentalCoilApi
    @DelicateCoroutinesApi
    @ExperimentalAnimationApi
    fun otpVerification(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp, otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val i = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(i)
                    Toast.makeText(applicationContext, "Vérification réusssie", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(applicationContext, "Mauvais code saisi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

}