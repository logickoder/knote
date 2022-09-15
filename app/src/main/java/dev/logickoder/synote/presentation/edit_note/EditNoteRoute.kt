package dev.logickoder.synote.presentation.edit_note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

        LaunchedEffect(key1 = Unit, block = {
            if (id == null) {
                viewModel.createNote()
            } else viewModel.getNote(id)
        })

        viewModel.note?.let {
            EditNoteScreen(
                modifier = modifier,
                note = it,
                isDarkMode = mainViewModel.darkMode.state(initial = false),
                switchDarkMode = mainViewModel::switchDarkMode,
                navigateBack = {
                    backStack.pop()
                },
            )
        }
    }
}