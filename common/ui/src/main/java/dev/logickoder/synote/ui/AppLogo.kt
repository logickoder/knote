package dev.logickoder.synote.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

@Composable
fun AppLogo(modifier: Modifier = Modifier) = Image(
    modifier = modifier.fillMaxWidth(0.45f),
    painter = painterResource(id = R.drawable.ui_ic_synote),
    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
    contentDescription = null,
)