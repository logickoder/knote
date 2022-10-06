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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.operation.replace
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import dev.logickoder.knote.domain.drawer
import dev.logickoder.knote.domain.route
import dev.logickoder.knote.notes.api.NoteId
import dev.logickoder.knote.notes.data.domain.NoteScreen
import dev.logickoder.knote.presentation.AppDrawer
import dev.logickoder.knote.presentation.DrawerItem
import dev.logickoder.knote.presentation.MainViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class Navigation(
    buildContext: BuildContext,
    startingRoute: Route,
    viewModel: MainViewModel,
    private val backStack: BackStack<Route> = BackStack(
        initialElement = startingRoute,
        savedStateMap = buildContext.savedStateMap,
    ).also {
        viewModel.initNavigation(it)
    },
) : ParentNode<Navigation.Route>(
    buildContext = buildContext,
    navModel = backStack,
) {
    private var scaffoldState: ScaffoldState? = null

    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = viewModel<MainViewModel>()
        val screen by viewModel.screen.collectAsState()
        val scope = rememberCoroutineScope()
        scaffoldState = rememberScaffoldState()

        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState!!,
            drawerContent = screen.drawer?.let { drawerItem ->
                {
                    AppDrawer(
                        selected = drawerItem,
                        itemClicked = { item ->
                            val route = item.route
                            if (item == DrawerItem.Logout) {
                                viewModel.logout()
                            }
                            when (route) {
                                Route.Settings, is Route.EditNote -> backStack.push(route)
                                else -> backStack.replace(route)
                            }
                            viewModel.updateScreen(route)
                            scope.launch {
                                scaffoldState?.drawerState?.close()
                            }
                        }
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
                backStack = backStack,
            )
            is Route.Notes -> NotesRoute(
                buildContext = buildContext,
                screen = navTarget.screen,
                backStack = backStack,
                openDrawer = {
                    scaffoldState?.drawerState?.open()
                }
            )
            is Route.EditNote -> EditNoteRoute(
                id = navTarget.id?.let { NoteId(it) },
                buildContext = buildContext,
                backStack = backStack,
            )
            Route.Settings -> SettingsRoute(
                buildContext = buildContext,
                backStack = backStack,
            )
        }
    }

    sealed class Route : Parcelable {

        @Parcelize
        object Login : Route()

        @Parcelize
        data class Notes(val screen: NoteScreen) : Route()

        @Parcelize
        data class EditNote(val id: Long?) : Route()

        @Parcelize
        object Settings : Route()
    }
}