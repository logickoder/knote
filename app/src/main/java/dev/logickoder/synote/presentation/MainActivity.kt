package dev.logickoder.synote.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.SynoteTheme

@AndroidEntryPoint
class MainActivity : NodeActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
        installSplashScreen()

        setContent {
            SynoteTheme {
                Surface(
                    color = AppTheme.colors.background,
                    content = {
                        viewModel.startingRoute?.let { route ->
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
        }
    }
}