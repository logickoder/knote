package dev.logickoder.synote.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.AppTheme

@Composable
fun AppLogo(modifier: Modifier = Modifier) = Image(
    modifier = modifier.fillMaxWidth(0.45f),
    painter = painterResource(id = R.drawable.ic_synote),
    colorFilter = ColorFilter.tint(AppTheme.colors.onBackground),
    contentDescription = null,
)