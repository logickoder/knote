package dev.logickoder.knote.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun NoteActionDialog(
    text: String,
    modifier: Modifier = Modifier,
    confirmActionColor: Color = MaterialTheme.colors.error,
    confirmAction: () -> Unit,
    dismissDialog: () -> Unit,
) = AlertDialog(
    modifier = modifier,
    onDismissRequest = dismissDialog,
    text = {
        Text(text)
    },
    confirmButton = {
        AppButton(
            onClick = {
                confirmAction()
                dismissDialog()
            },
            color = confirmActionColor,
            content = {
                Text(stringResource(id = R.string.ui_yes), color = Color.White)
            }
        )
    },
    dismissButton = {
        AppButton(onClick = dismissDialog, content = {
            Text(stringResource(id = R.string.ui_no))
        })
    }
)