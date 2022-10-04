package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import dev.logickoder.knote.settings.presentation.SettingsRoute

class SettingsRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        SettingsRoute(
            modifier = modifier,
            navigateBack = {
                backStack.pop()
            }
        )
    }
}