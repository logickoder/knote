package dev.logickoder.synote.presentation.notes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.presentation.MainViewModel
import dev.logickoder.synote.utils.state

class NotesRoute(
    buildContext: BuildContext,
    val backStack: BackStack<Navigation.Route>,
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) {
        val notesViewModel = viewModel<NotesViewModel>()
        val mainViewModel = viewModel<MainViewModel>()
        val notes by notesViewModel.notes.collectAsState()

        NotesScreen(
            modifier = modifier,
            notes = notes,
            editNote = {
                notesViewModel.editNote(it, backStack)
            },
            deleteNote = notesViewModel::deleteNote,
            onSearch = notesViewModel::search,
            isDarkMode = mainViewModel.darkMode.state(false),
            switchDarkMode = mainViewModel::switchDarkMode,
        )
    }
}