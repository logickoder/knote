package dev.logickoder.synote.presentation.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.logickoder.synote.core.theme.SynoteTheme

@Composable
fun SynoteAppBar(
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
    text: String,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    switchDarkMode: () -> Unit,
    navigation: Pair<ImageVector, () -> Unit>? = null,
) = DefaultAppBar(
    modifier = modifier,
    isDarkMode = isDarkMode,
    title = {
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.W600,
            )
        )
    },
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
        navigationIcon = navigation?.let {
            {
                IconButton(
                    onClick = it.second,
                    content = {
                        Icon(it.first, contentDescription = null)
                    }
                )
            }
        },
        title = title,
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

@Preview
@Composable
fun BrandAppBarPreview() = SynoteTheme {
    BrandAppBar(
        isDarkMode = false,
        text = "Edit Note",
        switchDarkMode = {}
    )
}

@Preview
@Composable
fun BrandAppBarWithNavigationPreview() = SynoteTheme {
    BrandAppBar(
        isDarkMode = false,
        text = "Edit Note",
        switchDarkMode = {},
        navigation = Icons.Default.KeyboardArrowLeft to {

        }
    )
}

@Preview
@Composable
fun SynoteAppBarPreview() = SynoteTheme {
    SynoteAppBar(isDarkMode = false) {

    }
}