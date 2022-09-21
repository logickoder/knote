package dev.logickoder.synote.presentation.edit_note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.presentation.MainViewModel
import dev.logickoder.synote.utils.state

class EditNoteRoute(
    buildContext: BuildContext,
    private val id: String?,
    private val backStack: BackStack<Navigation.Route>,
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = viewModel<EditNoteViewModel>()
        val mainViewModel = viewModel<MainViewModel>()
        val note by viewModel.note.collectAsState()

        LaunchedEffect(key1 = id, block = {
            viewModel.getNote(id)
        })

        EditNoteScreen(
            modifier = modifier,
            title = note?.title ?: "",
            content = note?.content ?: "",
            isDarkMode = mainViewModel.darkMode.state(initial = false),
            switchDarkMode = mainViewModel::switchDarkMode,
            navigateBack = {
                backStack.pop()
            },
            onTitleChanged = {},
            onContentChanged = {},
        )
    }
}