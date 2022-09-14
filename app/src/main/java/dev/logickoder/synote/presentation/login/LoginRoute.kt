package dev.logickoder.synote.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import dev.logickoder.synote.core.Navigation


class LoginRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) {
        val viewModel: LoginViewModel = viewModel()
        val context = LocalContext.current
        LoginScreen(
            modifier = modifier,
            uiState = viewModel.uiState,
            onLogin = {
                viewModel.performAuth(context, backStack)
            }
        )
    }
}