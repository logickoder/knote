package dev.logickoder.synote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import dev.logickoder.synote.notes.presentation.NotesRoute
import dev.logickoder.synote.presentation.MainViewModel

class NotesRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = viewModel<MainViewModel>()
        NotesRoute(
            modifier = modifier,
            isDarkMode = viewModel.darkMode.collectAsState().value,
            onSwitchDarkMode = viewModel::switchDarkMode,
            onNoteClick = {
                backStack.push(Navigation.Route.EditNote(it?.id))
            },
        )
    }
}