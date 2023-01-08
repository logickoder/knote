package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.note_list.presentation.NoteListRoute
import dev.logickoder.knote.presentation.MainViewModel
import kotlinx.coroutines.launch

class NoteListRoute(
    buildContext: BuildContext,
    private val screen: NoteListScreen,
    private val backStack: BackStack<Navigation.Route>,
    private val openDrawer: suspend () -> Unit,
) : Node(buildContext) {


    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = viewModel<MainViewModel>()
        val scope = rememberCoroutineScope()
        NoteListRoute(
            modifier = modifier,
            screen = screen,
            onNoteClick = {
                val route = Navigation.Route.EditNote(it?.id)
                backStack.push(route)
                viewModel.updateScreen(route)
            },
            openDrawer = {
                scope.launch {
                    openDrawer()
                }
            },
        )
    }
}