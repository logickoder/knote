package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import dev.logickoder.knote.login.presentation.LoginRoute
import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.presentation.MainViewModel

class LoginRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = viewModel<MainViewModel>()

        LoginRoute(
            modifier = modifier,
            onLogin = {
                val route = Navigation.Route.NoteList(NoteListScreen.Notes)
                viewModel.updateScreen(route)
                backStack.newRoot(route)
            }
        )
    }
}