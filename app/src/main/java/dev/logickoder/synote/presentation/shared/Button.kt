package dev.logickoder.synote.presentation.shared

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.logickoder.synote.R

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    color: Color? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) = Button(
    onClick = { onClick() },
    modifier = modifier,
    enabled = enabled && !isLoading,
    shape = MaterialTheme.shapes.medium,
    colors = ButtonDefaults.buttonColors(backgroundColor = color ?: MaterialTheme.colors.primary),
    content = {
        if (isLoading) Text(stringResource(id = R.string.please_wait)) else content()
    }
)