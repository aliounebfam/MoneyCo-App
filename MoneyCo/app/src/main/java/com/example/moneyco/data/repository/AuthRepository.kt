package com.example.moneyco.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.moneyco.data.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    context: Context,
    private val userRepository: UserRepository
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val PREF_LOGGED_IN_USER_ID = "logged_in_user_id"

    suspend fun registerUser(phoneNumber: String): Long {
        val user = User(
            phoneNumber = phoneNumber
        )
        
        val userId = userRepository.createUser(user)
        setLoggedInUserId(userId)
        userRepository.loginUser(userId)
        
        return userId
    }
    
    suspend fun registerGoogleUser(displayName: String, email: String, photoUrl: String): Long {
        val user = User(
            displayName = displayName,
            email = email,
            photoURl = photoUrl
        )
        
        val userId = userRepository.createUser(user)
        setLoggedInUserId(userId)
        userRepository.loginUser(userId)
        
        return userId
    }

    suspend fun loginUser(userId: Long): Boolean {
        userRepository.loginUser(userId)
        setLoggedInUserId(userId)
        return true
    }

    suspend fun logout() {
        userRepository.logoutCurrentUser()
        clearLoggedInUserId()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getLong(PREF_LOGGED_IN_USER_ID, -1) != -1L
    }

    fun getLoggedInUserId(): Long {
        return sharedPreferences.getLong(PREF_LOGGED_IN_USER_ID, -1)
    }

    private fun setLoggedInUserId(userId: Long) {
        sharedPreferences.edit().putLong(PREF_LOGGED_IN_USER_ID, userId).apply()
    }

    private fun clearLoggedInUserId() {
        sharedPreferences.edit().remove(PREF_LOGGED_IN_USER_ID).apply()
    }
}