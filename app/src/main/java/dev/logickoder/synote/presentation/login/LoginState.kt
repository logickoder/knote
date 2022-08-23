package dev.logickoder.synote.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.logickoder.synote.core.domain.MutableObservableState

internal enum class LoginCardType {
    Login, Register
}

internal class LoginState {
    val cardType = MutableObservableState(
        initial = LoginCardType.Login,
        update = { input: LoginCardType, _ -> input },
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
internal fun rememberLoginState() = remember {
    LoginState()
}