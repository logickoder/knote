package dev.logickoder.synote.core

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import dev.logickoder.synote.presentation.edit_note.EditNoteRoute
import dev.logickoder.synote.presentation.login.LoginRoute
import dev.logickoder.synote.presentation.notes.NotesRoute
import kotlinx.parcelize.Parcelize

class Navigation(
    buildContext: BuildContext,
    startingRoute: Route,
    private val backStack: BackStack<Route> = BackStack(
        initialElement = startingRoute,
        savedStateMap = buildContext.savedStateMap,
    ),
) : ParentNode<Navigation.Route>(
    buildContext = buildContext,
    navModel = backStack,
) {
    @Composable
    override fun View(modifier: Modifier) = Children(
        navModel = backStack,
        transitionHandler = rememberBackstackSlider(),
    )

    override fun resolve(routing: Route, buildContext: BuildContext): Node {
        return when (routing) {
            Route.Login -> LoginRoute(buildContext = buildContext, backStack = backStack)
            Route.Notes -> NotesRoute(buildContext = buildContext, backStack = backStack)
            is Route.EditNote -> EditNoteRoute(
                buildContext = buildContext,
                backStack = backStack,
                id = routing.id,
            )
        }
    }

    sealed class Route : Parcelable {

        @Parcelize
        object Login : Route()

        @Parcelize
        object Notes : Route()

        @Parcelize
        data class EditNote(val id: String?) : Route()
    }
}