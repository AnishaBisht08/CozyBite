package com.example.cozybite.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cozybite.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val repository = AuthRepository()

    private var _isLoading = MutableStateFlow(false)
     val isLoading = _isLoading.asStateFlow()


    fun registerUser(
        username: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ){
        _isLoading.value = true

        repository.registerUser(
            username = username,
            email = email,
            password = password
        ){ success , error ->

           onResult(success,error)
            _isLoading.value = false
        }
    }

    fun loginUser(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ){
        _isLoading.value = true

        repository.loginUser(
            email = email,
            password = password
        ){ success , error ->

           onResult(success,error)
            _isLoading.value = false
        }
    }



    fun signInWithGoogle(
        idToken: String,
        onResult: (Boolean) -> Unit
    ){

        repository.signInWithGoogle(
            idToken = idToken,
            onResult = onResult
        )
    }



    fun logout(context: Context) {
        viewModelScope.launch {
            try {
                repository.logout(context)
            } catch (e: Exception) {
                Log.e("AUTH", "Logout error: ${e.message}")
            }
        }
    }

}