package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.note_list.presentation.NoteListRoute
import dev.logickoder.knote.notes.data.model.NoteId

class NoteListRoute(
    buildContext: BuildContext,
    private val screen: NoteListScreen,
    private val onNoteClick: (NoteId?) -> Unit,
    private val openDrawer: () -> Unit,
) : Node(buildContext) {


    @Composable
    override fun View(modifier: Modifier) {
        NoteListRoute(
            modifier = modifier,
            screen = screen,
            onNoteClick = onNoteClick,
            openDrawer = openDrawer,
        )
    }
}