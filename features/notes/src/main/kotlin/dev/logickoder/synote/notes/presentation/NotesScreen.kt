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
import dev.logickoder.synote.notes.data.domain.NoteDomain
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
    notes: ImmutableList<NoteDomain>,
    inSelection: Boolean,
    editNote: (NoteId?) -> Unit,
    onSearch: (String) -> Unit,
    onSelectedChanged: (NoteId) -> Unit,
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
                        domain = note,
                        editNote = editNote,
                        selectedChanged = onSelectedChanged,
                        inSelection = inSelection,
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
        inSelection = false,
        editNote = {},
        onSearch = {},
        onSelectedChanged = { }
    )
}

@Preview
@Composable
private fun NotesScreenPreview() = SynoteTheme {
    NotesScreen(
        notes = (1..10).map {
            NoteDomain(
                note = Note(
                    id = NoteId(it.toLong()),
                    title = "Title $it",
                    content = "Content $it",
                    dateCreated = LocalDateTime.now(),
                ),
                selected = it % 2 == 0,
            )
        }.toImmutableList(),
        search = "My Note",
        inSelection = false,
        editNote = {},
        onSearch = {},
        onSelectedChanged = { },
    )
}