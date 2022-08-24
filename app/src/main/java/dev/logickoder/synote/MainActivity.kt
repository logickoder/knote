package dev.logickoder.synote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.SynoteTheme
import dev.logickoder.synote.presentation.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            SynoteTheme {
                Surface(
                    color = AppTheme.colors.background,
                    content = {
                        LoginScreen()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SynoteTheme {
        LoginScreen()
    }
}