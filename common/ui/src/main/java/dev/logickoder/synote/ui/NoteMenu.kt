package dev.logickoder.synote.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun NoteMenu(
    modifier: Modifier = Modifier,
    copyNote: () -> Unit,
    deleteNote: () -> Unit,
) = Box(
    modifier = modifier,
    content = {
        val context = LocalContext.current
        var showMenu by remember { mutableStateOf(false) }
        var showDeleteNoteDialog by remember { mutableStateOf(false) }

        IconButton(
            onClick = {
                showMenu = true
            },
            content = {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.MoreVert),
                    contentDescription = null,
                )
            }
        )
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    copyNote()
                    Toast.makeText(context, R.string.ui_copy_confirmation, Toast.LENGTH_SHORT)
                        .show()
                    showMenu = false
                },
                content = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Outlined.ContentCopy),
                        contentDescription = null,
                    )
                    Text(
                        text = stringResource(id = android.R.string.copy),
                    )
                }
            )
            DropdownMenuItem(
                onClick = {
                    showDeleteNoteDialog = true
                },
                content = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Outlined.Delete),
                        tint = MaterialTheme.colors.error,
                        contentDescription = null,
                    )
                    Text(
                        text = stringResource(id = R.string.ui_delete),
                        color = MaterialTheme.colors.error,
                    )
                }
            )
            if (showDeleteNoteDialog) {
                ConfirmDeleteNoteDialog(
                    deleteNote = deleteNote,
                    dismissDialog = {
                        showDeleteNoteDialog = false
                        showMenu = false
                    }
                )
            }
        }
    }
)

@Composable
private fun ConfirmDeleteNoteDialog(
    modifier: Modifier = Modifier,
    deleteNote: () -> Unit,
    dismissDialog: () -> Unit,
) = AlertDialog(
    modifier = modifier,
    onDismissRequest = dismissDialog,
    text = {
        Text(stringResource(id = R.string.ui_delete_confirmation))
    },
    confirmButton = {
        AppButton(
            onClick = {
                deleteNote()
                dismissDialog()
            }, color = MaterialTheme.colors.error,
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