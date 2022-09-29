package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.notes.R
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.ui.AppButton
import dev.logickoder.synote.ui.SynoteAppBar
import dev.logickoder.synote.ui.input.IconData
import dev.logickoder.synote.ui.input.InputField
import dev.logickoder.synote.ui.input.InputState
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.padding
import dev.logickoder.synote.ui.theme.secondaryPadding
import java.time.LocalDateTime

@Composable
internal fun NotesScreen(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    editNote: (NoteId?) -> Unit,
    onSearch: (String) -> Unit,
    isDarkMode: Boolean,
    switchDarkMode: () -> Unit,
) = Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
        SynoteAppBar(isDarkMode, switchDarkMode = switchDarkMode)
    },
    content = { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding),
            contentPadding = PaddingValues(padding()),
            content = {
                item {
                    NotesActionButtons(onNewNote = { editNote(null) })
                }
                item {
                    NotesSearchField(
                        modifier = Modifier.padding(vertical = secondaryPadding()),
                        onSearch = onSearch
                    )
                }
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
    }
)

@Composable
private fun NotesActionButtons(
    modifier: Modifier = Modifier,
    onNewNote: () -> Unit,
) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End,
    content = {
        AppButton(
            onClick = onNewNote,
            content = {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.Add),
                    contentDescription = null
                )
                Text(text = stringResource(id = R.string.notes_new))
            }
        )
    }
)

@Composable
private fun NotesSearchField(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    InputField(
        modifier = modifier.fillMaxWidth(),
        state = InputState(
            value = text,
            onValueChanged = {
                onSearch(it)
                text = it
            },
            icon = IconData(
                icon = Icons.Outlined.Search,
                alignEnd = false,
            ),
            placeholder = stringResource(id = R.string.notes_search),
        )
    )
}

@Preview
@Composable
private fun EmptyNotesScreenPreview() = SynoteTheme {
    NotesScreen(
        notes = emptyList(),
        editNote = {},
        onSearch = {},
        isDarkMode = false
    ) {}
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
        },
        editNote = {},
        onSearch = {},
        isDarkMode = true
    ) {}
}