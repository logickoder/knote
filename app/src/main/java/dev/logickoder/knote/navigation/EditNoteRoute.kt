package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.knote.edit_note.presentation.EditNoteRoute

class EditNoteRoute(
    private val noteId: Long?,
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