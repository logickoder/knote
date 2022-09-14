package dev.logickoder.synote.presentation.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

@Composable
fun DefaultAppBar(
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    switchDarkMode: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        title = {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    AppLogo()
                }
            )
        },
        actions = {
            IconButton(
                onClick = switchDarkMode,
                content = {
                    Icon(
                        painter = rememberVectorPainter(
                            if (isDarkMode) {
                                Icons.Outlined.LightMode
                            } else Icons.Outlined.DarkMode
                        ),
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = null,
                    )
                }
            )
        },
        elevation = 1.dp,
    )
}