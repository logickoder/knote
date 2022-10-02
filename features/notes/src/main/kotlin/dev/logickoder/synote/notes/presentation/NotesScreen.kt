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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteAction
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.data.domain.NoteDomain
import dev.logickoder.synote.ui.NoteActionDialog
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
    selected: Int,
    editNote: (NoteId?) -> Unit,
    deleteNotes: () -> Unit,
    archiveNotes: () -> Unit,
    onSearch: (String) -> Unit,
    onSelectedChanged: (NoteId) -> Unit,
    cancelSelection: () -> Unit,
) = Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
        var showDialog by remember { mutableStateOf<NoteAction?>(null) }

        if (selected > 0) {
            NotesInSelectionAppBar(
                selected = selected,
                noteCount = notes.size,
                modifier = Modifier.fillMaxWidth(),
                performAction = { showDialog = it },
                cancelSelection = cancelSelection,
            )
        } else NotesAppBar(
            search = search,
            onSearch = onSearch,
            modifier = Modifier.fillMaxWidth(),
        )

        showDialog?.let {
            NoteActionDialog(
                text = stringResource(
                    id = dev.logickoder.synote.ui.R.string.ui_confirmation_multiple,
                    it.name.lowercase()
                ),
                confirmAction = {
                    when (it) {
                        NoteAction.Archive -> archiveNotes()
                        NoteAction.Delete -> deleteNotes()
                    }
                },
                dismissDialog = { showDialog = null }
            )

        }
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
                        inSelection = selected > 0,
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
        selected = 0,
        editNote = {},
        deleteNotes = {},
        archiveNotes = {},
        onSearch = {},
        onSelectedChanged = { },
        cancelSelection = {}
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
                    action = null,
                ),
                selected = it % 2 == 0,
            )
        }.toImmutableList(),
        search = "My Note",
        selected = 1,
        editNote = {},
        deleteNotes = {},
        archiveNotes = {},
        onSearch = {},
        onSelectedChanged = { },
        cancelSelection = {}
    )
}