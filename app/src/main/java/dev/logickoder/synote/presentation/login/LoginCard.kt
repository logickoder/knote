package dev.logickoder.synote.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.presentation.shared.ErrorText
import dev.logickoder.synote.presentation.shared.LoadingButton
import dev.logickoder.synote.presentation.shared.input.Input
import dev.logickoder.synote.presentation.shared.input.InputState
import dev.logickoder.synote.presentation.shared.input.PasswordInput

@Composable
fun LoginCard(
    uiState: LoginState,
    onLogin: () -> Unit = {},
) = with(uiState) {
    Column(
        modifier = Modifier
            .background(shape = AppTheme.shapes.medium, color = AppTheme.colors.surface)
            .padding(padding()),
        verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
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
                    onValueChanged = {
                        username = it
                    },
                    required = true,
                    error = usernameError,
                ),
            )
            PasswordInput(
                state = InputState(
                    value = password,
                    onValueChanged = {
                        password = it
                    },
                    required = true,
                    error = passwordError,
                )
            )
            LoadingButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onLogin,
                isLoading = isLoading,
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
            loginError?.let { ErrorText(error = it) }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginCardLogin() = LoginCard(LoginState())

@Preview(showBackground = true)
@Composable
private fun LoginCardRegister() = LoginCard(LoginState().apply {
    isLogin = false
})

@Preview(showBackground = true)
@Composable
private fun LoginCardWithError() = LoginCard(LoginState().apply {
    loginError = "Login Error"
})