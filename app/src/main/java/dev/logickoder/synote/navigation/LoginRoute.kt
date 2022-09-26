package dev.logickoder.synote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import dev.logickoder.synote.login.presentation.LoginRoute

class LoginRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        LoginRoute(
            modifier = modifier,
            onLogin = {
                backStack.push(Navigation.Route.Notes)
            }
        )
    }
}