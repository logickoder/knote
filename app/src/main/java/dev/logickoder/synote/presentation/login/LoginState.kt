package dev.logickoder.synote.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.logickoder.synote.core.domain.MutableObservableState

class LoginState {
    val isLogin = MutableObservableState(
        initial = true,
        update = { input: Boolean, _ -> input },
        output = { it },
    )
    val username = MutableObservableState<String?, String?, String>(
        initial = null,
        update = { it, _ -> it },
        output = { it ?: "" }
    )

    val password = MutableObservableState<String?, String?, String>(
        initial = null,
        update = { it, _ -> it },
        output = { it ?: "" }
    )
}

@Composable
fun rememberLoginState() = remember {
    LoginState()
}