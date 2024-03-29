package dev.logickoder.knote.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.logickoder.knote.navigation.Navigation
import dev.logickoder.knote.notes.worker.NotesSyncWorker
import dev.logickoder.knote.settings.data.model.Theme
import dev.logickoder.knote.ui.theme.KNoteTheme

@AndroidEntryPoint
class MainActivity : NodeActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotesSyncWorker.sync(this)

        // Handle the splash screen transition.
        installSplashScreen()

        setContent {
            val theme by viewModel.theme.collectAsState()

            KNoteTheme(
                darkTheme = when (theme) {
                    Theme.Light -> false
                    Theme.Dark -> true
                    Theme.System -> isSystemInDarkTheme()
                },
                content = {
                    NodeHost(
                        integrationPoint = appyxIntegrationPoint,
                        factory = { context ->
                            Navigation(
                                buildContext = context,
                                startingRoute = viewModel.screen.value,
                                viewModel = viewModel,
                            )
                        }
                    )
                }
            )
        }
    }

    override fun onDestroy() {
        NotesSyncWorker.sync(this)
        super.onDestroy()
    }
}