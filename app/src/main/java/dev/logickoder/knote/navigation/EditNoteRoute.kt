package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.knote.edit_note.presentation.EditNoteRoute
import dev.logickoder.knote.notes.data.model.NoteId

class EditNoteRoute(
    private val noteId: NoteId?,
    buildContext: BuildContext,
    private val onBack: () -> Unit,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        EditNoteRoute(
            modifier = modifier,
            id = noteId,
            navigateBack = onBack
        )
    }
}