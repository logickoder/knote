package dev.logickoder.knote.notes.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.logickoder.knote.model.formatted
import dev.logickoder.knote.notes.data.domain.NoteItem
import dev.logickoder.knote.ui.theme.KNoteTheme
import dev.logickoder.knote.ui.theme.TextColor
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun Note(
    modifier: Modifier = Modifier,
    domain: NoteItem,
    inSelection: Boolean,
    editNote: (dev.logickoder.knote.notes.data.model.NoteId) -> Unit,
    selectedChanged: (dev.logickoder.knote.notes.data.model.NoteId) -> Unit,
) = with(domain) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .combinedClickable(
                onClick = {
                    if (inSelection || selected) {
                        selectedChanged(note.id)
                    } else editNote(note.id)
                },
                onLongClick = {
                    selectedChanged(note.id)
                }
            ),
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        border = if (selected) BorderStroke(1.dp, MaterialTheme.colors.primary) else null,
        content = {
            ListItem(
                modifier = Modifier
                    .fillMaxWidth(),
                singleLineSecondaryText = true,
                overlineText = {
                    Text(note.dateModified.formatted)
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
            )
        }
    )

}

@Preview
@Composable
private fun NotePreview() = KNoteTheme {
    Note(
        domain = NoteItem(
            note = dev.logickoder.knote.notes.data.model.Note(
                id = dev.logickoder.knote.notes.data.model.NoteId(1L),
                title = "Title",
                content = "Content",
                dateCreated = LocalDateTime.now(),
                action = null,
            ),
            selected = false,
        ),
        inSelection = false,
        editNote = {},
        selectedChanged = {}
    )
}

@Preview
@Composable
private fun SelectedNotePreview() = KNoteTheme {
    Note(
        domain = NoteItem(
            note = dev.logickoder.knote.notes.data.model.Note(
                id = dev.logickoder.knote.notes.data.model.NoteId(1L),
                title = "Title",
                content = "Content",
                dateCreated = LocalDateTime.now(),
                action = null,
            ),
            selected = true,
        ),
        inSelection = false,
        editNote = {},
        selectedChanged = {}
    )
}