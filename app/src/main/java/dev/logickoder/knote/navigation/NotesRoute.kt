package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import dev.logickoder.knote.notes.data.domain.NoteScreen
import dev.logickoder.knote.notes.presentation.NotesRoute
import dev.logickoder.knote.presentation.MainViewModel
import kotlinx.coroutines.launch

class NotesRoute(
    buildContext: BuildContext,
    private val screen: NoteScreen,
    private val backStack: BackStack<Navigation.Route>,
    private val openDrawer: suspend () -> Unit,
) : Node(buildContext) {


    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = viewModel<MainViewModel>()
        val scope = rememberCoroutineScope()
        NotesRoute(
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