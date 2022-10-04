package dev.logickoder.knote.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import dev.logickoder.knote.notes.api.NoteId
import dev.logickoder.knote.notes.presentation.NotesDrawerItem
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

    override fun resolve(navTarget: Route, buildContext: BuildContext): Node {
        return when (navTarget) {
            Route.Login -> LoginRoute(buildContext = buildContext, backStack = backStack)
            Route.Notes -> NotesRoute(buildContext = buildContext, backStack = backStack)
            is Route.EditNote -> EditNoteRoute(
                buildContext = buildContext,
                backStack = backStack,
                id = navTarget.id?.let { NoteId(it) },
            )
            Route.Settings -> SettingsRoute(buildContext = buildContext, backStack = backStack)
        }
    }

    sealed class Route : Parcelable {

        @Parcelize
        object Login : Route()

        @Parcelize
        object Notes : Route()

        @Parcelize
        data class EditNote(val id: Long?) : Route()

        @Parcelize
        object Settings : Route()
    }
}

val NotesDrawerItem.route: Navigation.Route
    get() = when (this) {
        NotesDrawerItem.Notes,
        NotesDrawerItem.Archive,
        NotesDrawerItem.Trash -> Navigation.Route.Notes
        NotesDrawerItem.Settings -> Navigation.Route.Settings
    }