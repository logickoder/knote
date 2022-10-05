package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import com.bumble.appyx.navmodel.backstack.operation.push
import dev.logickoder.knote.notes.presentation.NotesDrawerItem
import dev.logickoder.knote.notes.presentation.NotesRoute
import dev.logickoder.knote.presentation.MainViewModel

class NotesRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {


    @Composable
    override fun View(modifier: Modifier) {

        val viewModel = viewModel<MainViewModel>()
        NotesRoute(
            modifier = modifier,
            onNoteClick = {
                backStack.push(Navigation.Route.EditNote(it?.id))
            },
            onDrawerItemClicked = {
                when (it) {
                    NotesDrawerItem.Settings -> backStack.push(it.route)
                    NotesDrawerItem.Logout -> viewModel.logout {
                        backStack.newRoot(it.route)
                    }
                    else -> {}
                }
            }
        )
    }
}