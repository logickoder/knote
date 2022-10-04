package dev.logickoder.synote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import dev.logickoder.synote.edit_note.presentation.EditNoteRoute
import dev.logickoder.synote.notes.api.NoteId

class EditNoteRoute(
    private val id: NoteId?,
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        EditNoteRoute(
            modifier = modifier,
            id = id,
            navigateBack = {
                backStack.pop()
            }
        )
    }
}