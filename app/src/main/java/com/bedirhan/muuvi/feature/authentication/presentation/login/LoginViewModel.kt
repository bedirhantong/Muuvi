package com.bedirhan.muuvi.feature.authentication.presentation.login

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class LoginViewModel (private val state: SavedStateHandle) : ViewModel(){

    companion object {
        private const val EMAIL_KEY = "email"
        private const val PASSWORD_KEY = "password"
    }
    var email: String
        get() = state[EMAIL_KEY] ?: ""
        set(value) {
            state[EMAIL_KEY] = value
        }

    var password: String
        get() = state[PASSWORD_KEY] ?: ""
        set(value) {
            state[PASSWORD_KEY] = value
        }

    fun isLoginButtonEnabled(): Boolean {
        return email.length > 2 && password.length > 2
    }

    fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(): Boolean {
        return password.length > 8 && password.any { it.isUpperCase() } && password.any { !it.isLetterOrDigit() }
    }
}