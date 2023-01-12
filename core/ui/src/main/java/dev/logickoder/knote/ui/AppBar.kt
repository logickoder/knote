package dev.logickoder.knote.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.logickoder.knote.ui.theme.KNoteTheme

@Composable
fun knoteAppBar(
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    switchDarkMode: () -> Unit,
) = DefaultAppBar(
    modifier = modifier,
    isDarkMode = isDarkMode,
    title = {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                AppLogo()
            }
        )
    },
    switchDarkMode = switchDarkMode,
)

@Composable
fun BrandAppBar(
    title: @Composable () -> Unit,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    switchDarkMode: () -> Unit,
    navigation: Pair<ImageVector, () -> Unit>? = null,
) = DefaultAppBar(
    modifier = modifier,
    isDarkMode = isDarkMode,
    title = title,
    switchDarkMode = switchDarkMode,
    navigation = navigation,
)

@Composable
fun DefaultAppBar(
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    switchDarkMode: () -> Unit,
    navigation: Pair<ImageVector, () -> Unit>? = null,
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        content = {
            navigation?.let {
                IconButton(
                    onClick = it.second,
                    content = {
                        Icon(it.first, contentDescription = null)
                    }
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                content = {
                    title()
                }
            )
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

@Preview
@Composable
fun BrandAppBarPreview() = KNoteTheme {
    BrandAppBar(
        isDarkMode = false,
        title = { Text("Edit Note") },
        switchDarkMode = {}
    )
}

@Preview
@Composable
fun BrandAppBarWithNavigationPreview() = KNoteTheme {
    BrandAppBar(
        isDarkMode = false,
        title = { Text("Edit Note") },
        switchDarkMode = {},
        navigation = Icons.Default.KeyboardArrowLeft to {

        }
    )
}

@Preview
@Composable
fun knoteAppBarPreview() = KNoteTheme {
    knoteAppBar(isDarkMode = false) {

    }
}