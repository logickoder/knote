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
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.synote.presentation.MainViewModel

@Composable
fun DefaultAppBar(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel()
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
                onClick = viewModel::switchDarkMode,
                content = {
                    Icon(
                        painter = rememberVectorPainter(
                            if (viewModel.isDarkMode) {
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