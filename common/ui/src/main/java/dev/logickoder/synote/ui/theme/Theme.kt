package dev.logickoder.synote.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.logickoder.synote.ui.theme.*

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    background = BackgroundLightColor,
    surface = SurfaceLightColor,
)

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    background = BackgroundDarkColor,
    surface = SurfaceDarkColor,
)

@Composable
fun SynoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    val colors = if (darkTheme) {
        DarkColorPalette
    } else LightColorPalette

    MaterialTheme(
        colors = colors,
        shapes = Shapes,
        typography = Typography,
        content = content
    )

    SideEffect {
        systemUiController.setStatusBarColor(
            color = if (darkTheme) PrimaryColor else Color.Transparent,
            darkIcons = !darkTheme,
        )
    }
}