package dev.logickoder.synote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import dev.logickoder.synote.edit_note.presentation.EditNoteRoute
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.presentation.MainViewModel

class EditNoteRoute(
    private val id: NoteId?,
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = viewModel<MainViewModel>()
        EditNoteRoute(
            modifier = modifier,
            id = id,
            isDarkMode = viewModel.darkMode.collectAsState().value,
            switchDarkMode = viewModel::switchDarkMode,
            navigateBack = {
                backStack.pop()
            }
        )
    }
}