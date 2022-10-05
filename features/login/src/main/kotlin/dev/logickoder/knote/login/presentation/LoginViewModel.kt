package dev.logickoder.knote.login.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.knote.auth.api.AuthRepository
import dev.logickoder.knote.model.ResultWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    app: Application,
    private val repository: AuthRepository,
) : AndroidViewModel(app) {

    private val context: Context
        get() = getApplication()

    val uiState = LoginState().apply {
        login = { onLogin ->
            if (isValidInput()) {
                viewModelScope.launch {
                    isLoading = true
                    val result = if (isLogin) {
                        repository.login(email, password)
                    } else repository.register(email, username, password)
                    when (result) {
                        is ResultWrapper.Failure -> loginError = result.error.message
                        is ResultWrapper.Success -> onLogin()
                    }
                    isLoading = false
                }
            }
        }
        googleLogin = { credential, onLogin ->
            viewModelScope.launch {
                when (val result = repository.signInWithCredential(credential)) {
                    is ResultWrapper.Failure -> loginError = result.error.message
                    is ResultWrapper.Success -> onLogin()
                }
                isGoogleLoading = false
            }
        }
    }

    private fun isValidInput(): Boolean = with(uiState) {
        val errorText = context.getString(dev.logickoder.knote.ui.R.string.ui_required_field)

        emailError = if (email.isBlank()) {
            errorText
        } else null
        usernameError = if (isLogin.not() && username.isBlank()) {
            errorText
        } else null
        passwordError = if (password.isBlank()) {
            errorText
        } else null

        return emailError == null && usernameError == null && passwordError == null
    }
}