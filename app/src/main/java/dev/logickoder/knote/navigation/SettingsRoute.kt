package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.knote.settings.presentation.SettingsRoute

class SettingsRoute(
    buildContext: BuildContext,
    private val onBack: () -> Unit,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        SettingsRoute(
            modifier = modifier,
            navigateBack = onBack,
        )
    }
}