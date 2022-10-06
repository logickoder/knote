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
import dev.logickoder.knote.settings.api.Theme
import dev.logickoder.knote.ui.theme.KNoteTheme

@AndroidEntryPoint
class MainActivity : NodeActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
        installSplashScreen()

        setContent {
            val theme by viewModel.theme.collectAsState()
            val screen by viewModel.startScreen.collectAsState()

            KNoteTheme(
                darkTheme = when (theme) {
                    Theme.Light -> false
                    Theme.Dark -> true
                    Theme.System -> isSystemInDarkTheme()
                },
                content = {
                    screen?.let {
                        NodeHost(
                            integrationPoint = integrationPoint,
                            factory = { context ->
                                Navigation(
                                    buildContext = context,
                                    startingRoute = it,
                                    viewModel = viewModel,
                                )
                            }
                        )
                    }
                }
            )
        }
    }
}