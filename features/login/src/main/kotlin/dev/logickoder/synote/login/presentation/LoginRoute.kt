package dev.logickoder.synote.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
) {
    val viewModel: LoginViewModel = viewModel()
    LoginScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onLogin = {
            viewModel.performAuth(onLogin)
        }
    )
}