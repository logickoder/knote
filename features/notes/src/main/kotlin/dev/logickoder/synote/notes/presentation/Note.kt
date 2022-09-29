package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.model.formatted
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.TextColor
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun Note(
    modifier: Modifier = Modifier,
    note: Note,
    selected: Boolean = false,
    editNote: (NoteId) -> Unit,
    selectedChanged: (Boolean) -> Unit,
) = ListItem(
    modifier = modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.large)
        .background(
            MaterialTheme.colors.surface.copy(
                alpha = if (selected) 0.5f else 1f
            )
        )
        .combinedClickable(
            onClick = {
                editNote(note.id)
            },
            onLongClick = {
                selectedChanged(selected.not())
            }
        ),
    singleLineSecondaryText = true,
    overlineText = {
        val date = note.dateModified ?: note.dateCreated
        Text(date.formatted)
    },
    text = {
        Text(
            text = note.title,
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.W500,
            ),
            maxLines = 1,
        )
    },
    secondaryText = if (note.content.isNotBlank()) {
        {
            Text(
                text = note.content,
                color = TextColor,
                maxLines = 1,
            )
        }
    } else null,
    trailing = if (selected) {
        {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
            )
        }
    } else null,
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
        editNote = {},
        selectedChanged = {}
    )
}

@Preview
@Composable
private fun SelectedNotePreview() = SynoteTheme {
    Note(
        note = Note(
            id = NoteId(1L),
            title = "Title",
            content = "Content",
            dateCreated = LocalDateTime.now(),
        ),
        selected = true,
        editNote = {},
        selectedChanged = {},
    )
}