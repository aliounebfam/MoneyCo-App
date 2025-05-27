package com.example.moneyco.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyco.data.User
import com.example.moneyco.data.repository.AuthRepository
import com.example.moneyco.data.repository.UserRepository
import com.example.moneyco.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _authState = MutableStateFlow<LoadingState>(LoadingState.IDLE)
    val authState: StateFlow<LoadingState> = _authState

    init {
        viewModelScope.launch {
            if (authRepository.isUserLoggedIn()) {
                val userId = authRepository.getLoggedInUserId()
                userRepository.getUserById(userId).collect { user ->
                    _currentUser.value = user
                }
            }
        }
    }

    fun signInWithPhone(phoneNumber: String) {
        viewModelScope.launch {
            _authState.value = LoadingState.LOADING
            try {
                val userId = authRepository.registerUser(phoneNumber)
                _authState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _authState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun signInWithGoogle(displayName: String, email: String, photoUrl: String) {
        viewModelScope.launch {
            _authState.value = LoadingState.LOADING
            try {
                val userId = authRepository.registerGoogleUser(displayName, email, photoUrl)
                _authState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _authState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _authState.value = LoadingState.LOADING
            try {
                authRepository.logout()
                _currentUser.value = null
                _authState.value = LoadingState.IDLE
            } catch (e: Exception) {
                _authState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}