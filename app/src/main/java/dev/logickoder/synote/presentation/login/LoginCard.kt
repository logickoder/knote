package dev.logickoder.synote.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.presentation.shared.input.Input
import dev.logickoder.synote.presentation.shared.input.InputState
import dev.logickoder.synote.presentation.shared.input.PasswordInput
import dev.logickoder.synote.utils.collectAsState

@Composable
internal fun LoginCard(
    viewModel: LoginViewModel,
) {
    val isLogin by viewModel.isLogin.collectAsState()
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()

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
                    onValueChanged = viewModel.username::emit,
                ),
            )
            PasswordInput(
                state = InputState(
                    value = password,
                    onValueChanged = viewModel.password::emit
                )
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::performAuth,
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
    LoginCard(viewModel())
}

@Preview(showBackground = true)
@Composable
private fun LoginCardRegister() {
    val viewModel: LoginViewModel = viewModel()
    viewModel.isLogin.emit(false)
    LoginCard(viewModel)
}