package dev.logickoder.synote.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.presentation.shared.input.Input
import dev.logickoder.synote.presentation.shared.input.InputState
import dev.logickoder.synote.utils.collectAsState

@Composable
internal fun LoginCard(
    uiState: LoginState,
) {
    val cardType by uiState.cardType.collectAsState()
    val username by uiState.username.collectAsState()
    val password by uiState.password.collectAsState()

    Column(
        modifier = Modifier
            .background(shape = AppTheme.shapes.medium, color = AppTheme.colors.surface)
            .padding(padding()),
        verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            val isLogin = cardType == LoginCardType.Login
            Text(
                text = stringResource(
                    if (isLogin) {
                        R.string.login_to_your_account
                    } else R.string.create_your_account
                ),
                style = AppTheme.typography.body1,
            )
            Input(
                title = stringResource(id = R.string.username),
                state = InputState(
                    value = username,
                    onValueChanged = uiState.username::emit,
                ),
            )
            Input(
                title = stringResource(id = R.string.password),
                state = InputState(
                    value = password,
                    onValueChanged = uiState.password::emit,
                    visualTransformation = PasswordVisualTransformation(),
                    icon = Alignment.End to Icons.Outlined.RemoveRedEye,
                )
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { },
                content = {
                    Text(
                        text = stringResource(
                            id = if (isLogin) {
                                R.string.login
                            } else R.string.register
                        )
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginCardLogin() {
    LoginCard(rememberLoginState())
}

@Preview(showBackground = true)
@Composable
private fun LoginCardRegister() {
    val state = rememberLoginState()
    state.cardType.emit(LoginCardType.Register)
    LoginCard(state)
}