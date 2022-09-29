package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.padding
import dev.logickoder.synote.ui.theme.secondaryPadding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDateTime

@Composable
internal fun NotesScreen(
    modifier: Modifier = Modifier,
    search: String,
    notes: ImmutableList<Note>,
    editNote: (NoteId?) -> Unit,
    onSearch: (String) -> Unit,
) = Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
        NotesAppBar(
            search = search,
            onSearch = onSearch,
            modifier = Modifier.fillMaxWidth(),
        )
    },
    content = { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding),
            contentPadding = PaddingValues(padding()),
            content = {
                items(notes) { note ->
                    Note(
                        note = note,
                        editNote = editNote,
                        selected = false,
                        selectedChanged = {},
                    )
                    Spacer(modifier = Modifier.height(secondaryPadding()))
                }
            }
        )
    },
    floatingActionButton = {
        FloatingActionButton(
            onClick = { editNote(null) },
            backgroundColor = MaterialTheme.colors.primary,
            content = {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.Add),
                    contentDescription = null
                )
            }
        )
    }
)

@Preview
@Composable
private fun EmptyNotesScreenPreview() = SynoteTheme {
    NotesScreen(
        notes = persistentListOf(),
        search = "",
        editNote = {},
        onSearch = {},
    )
}

@Preview
@Composable
private fun NotesScreenPreview() = SynoteTheme {
    NotesScreen(
        notes = (1L..10L).map {
            Note(
                id = NoteId(it),
                title = "Title $it",
                content = "Content $it",
                dateCreated = LocalDateTime.now(),
            )
        }.toImmutableList(),
        search = "My Note",
        editNote = {},
        onSearch = {},
    )
}