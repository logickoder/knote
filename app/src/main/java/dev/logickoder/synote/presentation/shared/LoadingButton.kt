package dev.logickoder.synote.presentation.shared

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.AppTheme

@Composable
fun LoadingButton(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) = Button(
    onClick = { onClick() },
    modifier = modifier,
    enabled = enabled && !isLoading,
    shape = AppTheme.shapes.medium,
    colors = ButtonDefaults.buttonColors(backgroundColor = color ?: AppTheme.colors.primary),
    content = {
        if (isLoading) Text(stringResource(id = R.string.please_wait)) else content()
    }
)