package dev.logickoder.synote.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorText(error: String) {
    Text(
        text = error,
        style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error),
    )
}