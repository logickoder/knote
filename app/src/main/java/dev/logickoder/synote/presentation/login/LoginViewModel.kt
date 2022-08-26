package dev.logickoder.synote.presentation.login

import android.content.Context
import android.text.TextUtils
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.replace
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.R
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.data.repository.AuthRepository
import dev.logickoder.synote.utils.ResultWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginState {
    var username by mutableStateOf("mikethedev")
    var usernameError by mutableStateOf<String?>(null)

    var password by mutableStateOf("test")
    var passwordError by mutableStateOf<String?>(null)

    var loginError by mutableStateOf<String?>(null)

    var isLogin by mutableStateOf(true)
    var isLoading by mutableStateOf(false)
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {
    val uiState = LoginState()

    fun performAuth(
        context: Context,
        backStack: BackStack<Navigation.Route>,
    ): Unit = with(uiState) {
        if (isValidInput(context)) {
            viewModelScope.launch {
                isLoading = true
                val result = if (isLogin) {
                    repository.login(username, password)
                } else repository.register(username, password)
                when (result) {
                    is ResultWrapper.Failure -> loginError = result.error.message
                    is ResultWrapper.Success -> backStack.replace(Navigation.Route.Notes)
                }
                isLoading = false
            }
        }
    }

    private fun isValidInput(
        context: Context
    ): Boolean = with(uiState) {
        val errorText = context.getString(R.string.required_field)

        usernameError = if (TextUtils.isEmpty(username)) {
            errorText
        } else null
        passwordError = if (TextUtils.isEmpty(password)) {
            errorText
        } else null

        return usernameError == null && passwordError == null
    }
}