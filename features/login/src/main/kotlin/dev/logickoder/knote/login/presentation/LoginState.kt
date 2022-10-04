package dev.logickoder.knote.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

internal class LoginState {
    var email by mutableStateOf("chukwudumebiorazulike@gmail.com")
    var emailError by mutableStateOf<String?>(null)

    var username by mutableStateOf("logickoder")
    var usernameError by mutableStateOf<String?>(null)

    var password by mutableStateOf("test")
    var passwordError by mutableStateOf<String?>(null)

    var loginError by mutableStateOf<String?>(null)

    var isLogin by mutableStateOf(true)
    var isLoading by mutableStateOf(false)
}