package dev.logickoder.synote.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.logickoder.synote.navigation.Navigation
import dev.logickoder.synote.ui.theme.SynoteTheme

@AndroidEntryPoint
class MainActivity : NodeActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
        installSplashScreen()

        setContent {
            val darkMode by viewModel.darkMode.collectAsState()
            val startingRoute by viewModel.startingRoute.collectAsState()
            SynoteTheme(
                darkTheme = darkMode,
                content = {
                    Surface(
                        color = MaterialTheme.colors.background,
                        content = {
                            startingRoute?.let { route ->
                                NodeHost(integrationPoint = integrationPoint) {
                                    Navigation(
                                        buildContext = it,
                                        startingRoute = route,
                                    )
                                }
                            }
                        }
                    )
                }
            )
        }
    }
}