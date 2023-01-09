package dev.logickoder.knote.navigation

import android.os.Parcelable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.operation.replace
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.notes.data.model.NoteId
import dev.logickoder.knote.presentation.AppDrawer
import dev.logickoder.knote.presentation.DrawerItem
import dev.logickoder.knote.presentation.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlin.properties.Delegates

class Navigation(
    buildContext: BuildContext,
    startingRoute: Route,
    private val viewModel: MainViewModel,
    private val backStack: BackStack<Route> = BackStack(
        initialElement = startingRoute,
        savedStateMap = buildContext.savedStateMap,
    ),
) : ParentNode<Navigation.Route>(
    buildContext = buildContext,
    navModel = backStack,
) {
    private var scaffoldState by Delegates.notNull<ScaffoldState>()
    private var scope by Delegates.notNull<CoroutineScope>()

    init {
        viewModel.watchBackStackChanges(backStack)
    }

    private fun onDrawerItemClicked(item: DrawerItem) {
        if (item == DrawerItem.Logout) {
            viewModel.logout()
        }
        when (item.route) {
            Route.Settings, is Route.EditNote -> backStack.push(item.route)
            else -> backStack.replace(item.route)
        }
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }


    @Composable
    override fun View(modifier: Modifier) {
        val screen by viewModel.screen.collectAsState()
        scaffoldState = rememberScaffoldState()
        scope = rememberCoroutineScope()

        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            drawerContent = screen.drawer?.let { drawerItem ->
                {
                    AppDrawer(
                        selected = drawerItem,
                        itemClicked = ::onDrawerItemClicked
                    )
                }
            },
            drawerShape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp),
            drawerBackgroundColor = MaterialTheme.colors.background,
            content = { padding ->
                Children(
                    modifier = Modifier.padding(padding),
                    navModel = backStack,
                    transitionHandler = rememberBackstackSlider(),
                )
            }
        )
    }

    override fun resolve(navTarget: Route, buildContext: BuildContext): Node {
        return when (navTarget) {
            Route.Login -> LoginRoute(
                buildContext = buildContext,
                onLogin = {
                    backStack.newRoot(Route.NoteList(NoteListScreen.Notes))
                },
            )

            is Route.NoteList -> NoteListRoute(
                buildContext = buildContext,
                screen = navTarget.screen,
                onNoteClick = {
                    backStack.push(Route.EditNote(it?.id))
                },
                openDrawer = {
                    scope.launch { scaffoldState.drawerState.open() }
                },
            )

            is Route.EditNote -> EditNoteRoute(
                noteId = navTarget.id?.let { NoteId(it) },
                buildContext = buildContext,
                onBack = { backStack.pop() },
            )

            Route.Settings -> SettingsRoute(
                buildContext = buildContext,
                onBack = { backStack.pop() },
            )
        }
    }

    sealed class Route : Parcelable {

        @Parcelize
        object Login : Route()

        @Parcelize
        data class NoteList(val screen: NoteListScreen) : Route()

        @Parcelize
        data class EditNote(val id: Long?) : Route()

        @Parcelize
        object Settings : Route()
    }
}