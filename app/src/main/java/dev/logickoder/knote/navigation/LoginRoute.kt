package dev.logickoder.knote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.knote.login.presentation.LoginRoute
import dev.logickoder.knote.notes.worker.NotesSyncWorker

class LoginRoute(
    buildContext: BuildContext,
    private val onLogin: () -> Unit,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val context = LocalContext.current
        val login = remember {
            {
                NotesSyncWorker.sync(context)
                onLogin()
            }
        }
        LoginRoute(
            modifier = modifier,
            onLogin = login,
        )
    }
}