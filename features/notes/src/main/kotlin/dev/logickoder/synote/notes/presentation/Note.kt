package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.ui.NoteMenu
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.TextColor
import dev.logickoder.synote.ui.theme.padding
import dev.logickoder.synote.ui.theme.secondaryPadding
import java.time.LocalDateTime

@Composable
internal fun Note(
    modifier: Modifier = Modifier,
    note: Note,
    deleteNote: (NoteId) -> Unit,
    editNote: (NoteId) -> Unit,
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium),
    content = {
        val clipboardManager = LocalClipboardManager.current

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
        NoteMenu(
            modifier = Modifier
                .padding(secondaryPadding())
                .align(Alignment.TopEnd),
            deleteNote = {
                deleteNote(note.id)
            },
            copyNote = {
                clipboardManager.setText(AnnotatedString((note.content)))
            }
        )
    }
)

@Preview
@Composable
private fun NotePreview() = SynoteTheme {
    Note(
        note = Note(
            id = NoteId(1L),
            title = "Title",
            content = "Content",
            dateCreated = LocalDateTime.now(),
        ),
        deleteNote = {},
        editNote = {},
    )
}