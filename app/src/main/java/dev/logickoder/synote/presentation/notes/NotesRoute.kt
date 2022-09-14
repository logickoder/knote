package dev.logickoder.synote.presentation.notes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.presentation.MainViewModel

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

        LaunchedEffect(key1 = Unit, block = {
            notesViewModel.getNotes()
        })
        NotesScreen(
            modifier = modifier,
            notes = notesViewModel.notes.collectAsState(initial = emptyList()).value,
            editNote = {
                notesViewModel.editNote(it, backStack)
            },
            deleteNote = notesViewModel::deleteNote,
            onSearch = notesViewModel::search,
            isDarkMode = mainViewModel.isDarkMode,
            switchDarkMode = mainViewModel::switchDarkMode,
        )
    }
}