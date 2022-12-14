package dev.logickoder.knote.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.knote.login.R
import dev.logickoder.knote.ui.AppButton
import dev.logickoder.knote.ui.ErrorText
import dev.logickoder.knote.ui.input.Input
import dev.logickoder.knote.ui.input.InputState
import dev.logickoder.knote.ui.input.PasswordInput
import dev.logickoder.knote.ui.theme.padding
import dev.logickoder.knote.ui.theme.secondaryPadding

@Composable
internal fun LoginCard(
    state: LoginState,
    onLogin: () -> Unit,
) = with(state) {
    Column(
        modifier = Modifier
            .background(shape = MaterialTheme.shapes.medium, color = MaterialTheme.colors.surface)
            .padding(padding()),
        verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Text(
                text = stringResource(
                    if (isLogin) {
                        R.string.login_to_your_account
                    } else R.string.login_create_your_account
                ),
                style = MaterialTheme.typography.body1,
            )
            Input(
                title = stringResource(id = R.string.login_email),
                state = InputState(
                    value = email,
                    onValueChanged = {
                        email = it
                    },
                    required = true,
                    error = emailError,
                ),
            )
            if (isLogin.not()) {
                Input(
                    title = stringResource(id = R.string.login_username),
                    state = InputState(
                        value = username,
                        onValueChanged = {
                            username = it
                        },
                        required = true,
                        error = usernameError,
                    ),
                )
            }
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
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    login(onLogin)
                },
                isLoading = isLoading,
                content = {
                    Text(
                        text = stringResource(
                            id = if (isLogin) {
                                R.string.login
                            } else R.string.login_register
                        )
                    )
                }
            )
            Text(stringResource(R.string.login_or))
            GoogleSignInButton(
                loading = isGoogleLoading,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    isGoogleLoading = true
                },
                onError = {
                    loginError = it
                    isGoogleLoading = false
                },
                login = { credentials ->
                    googleLogin(credentials, onLogin)
                }
            )
            loginError?.let { ErrorText(error = it) }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginCardLogin() = LoginCard(
    state = LoginState(),
    onLogin = {},
)

@Preview(showBackground = true)
@Composable
private fun LoginCardRegister() = LoginCard(
    state = LoginState().apply {
        isLogin = false
    },
    onLogin = {},
)

@Preview(showBackground = true)
@Composable
private fun LoginCardWithError() = LoginCard(
    state = LoginState().apply {
        loginError = "Login Error"
    },
    onLogin = {},
)