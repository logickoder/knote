package dev.logickoder.knote.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import dev.logickoder.knote.edit_note.presentation.EditNoteRoute
import dev.logickoder.knote.notes.data.model.NoteId
import dev.logickoder.knote.presentation.MainViewModel

class EditNoteRoute(
    private val id: NoteId?,
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = viewModel<MainViewModel>()

        BackHandler {
            viewModel.pop(backStack)
        }
        EditNoteRoute(
            modifier = modifier,
            id = id,
            navigateBack = {
                viewModel.pop(backStack)
            }
        )
    }
}