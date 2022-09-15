package dev.logickoder.synote.presentation.notes

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.SynoteTheme
import dev.logickoder.synote.core.theme.TextColor
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.presentation.shared.AppButton
import java.time.LocalDateTime

@Composable
fun Note(
    modifier: Modifier = Modifier,
    note: NoteEntity,
    deleteNote: (String) -> Unit,
    editNote: (String) -> Unit,
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium),
    content = {
        val clipboardManager = LocalClipboardManager.current
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .clickable {
                    editNote(note.id)
                }
                .padding(padding()),
            verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
            content = {
                if (note.title.isNotBlank()) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.W500,
                        )
                    )
                }
                if (note.content.isNotBlank()) {
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.body2,
                        color = TextColor,
                        maxLines = 1
                    )
                }
            }
        )
        NoteContextActions(
            modifier = Modifier
                .padding(secondaryPadding())
                .align(Alignment.TopEnd),
            deleteNote = {
                deleteNote(note.id)
            },
            copyNote = {
                clipboardManager.setText(AnnotatedString((note.content)))
                Toast.makeText(context, R.string.copy_confirmation, Toast.LENGTH_SHORT).show()
            }
        )
    }
)

@Composable
private fun NoteContextActions(
    modifier: Modifier = Modifier,
    copyNote: () -> Unit,
    deleteNote: () -> Unit,
) = Box(modifier = modifier, content = {
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
                    text = stringResource(id = R.string.delete),
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
})

@Composable
private fun ConfirmDeleteNoteDialog(
    modifier: Modifier = Modifier,
    deleteNote: () -> Unit,
    dismissDialog: () -> Unit,
) = AlertDialog(
    modifier = modifier,
    onDismissRequest = dismissDialog,
    text = {
        Text(stringResource(id = R.string.delete_confirmation))
    },
    confirmButton = {
        AppButton(
            onClick = {
                deleteNote()
                dismissDialog()
            }, color = MaterialTheme.colors.error,
            content = {
                Text(stringResource(id = R.string.yes), color = Color.White)
            }
        )
    },
    dismissButton = {
        AppButton(onClick = dismissDialog, content = {
            Text(stringResource(id = R.string.no))
        })
    }
)

@Preview
@Composable
fun NotePreview() = SynoteTheme {
    Note(
        note = NoteEntity(
            id = "1",
            userId = "1",
            title = "Title",
            content = "Content",
            dateCreated = LocalDateTime.now().toString(),
        ),
        deleteNote = {},
        editNote = {},
    )
}