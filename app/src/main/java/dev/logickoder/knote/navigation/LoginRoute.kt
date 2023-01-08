package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.knote.login.presentation.LoginRoute

class LoginRoute(
    buildContext: BuildContext,
    private val onLogin: () -> Unit,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        LoginRoute(
            modifier = modifier,
            onLogin = onLogin,
        )
    }
}