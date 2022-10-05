package dev.logickoder.knote.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.AuthCredential

internal class LoginState {
    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)

    var username by mutableStateOf("")
    var usernameError by mutableStateOf<String?>(null)

    var password by mutableStateOf("")
    var passwordError by mutableStateOf<String?>(null)

    var loginError by mutableStateOf<String?>(null)

    var isLogin by mutableStateOf(true)
    var isLoading by mutableStateOf(false)

    var isGoogleLoading by mutableStateOf(false)

    lateinit var login: (() -> Unit) -> Unit
    lateinit var googleLogin: (AuthCredential, () -> Unit) -> Unit
}