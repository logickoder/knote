package dev.logickoder.synote.presentation.notes

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
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.SynoteTheme
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.presentation.shared.AppButton
import dev.logickoder.synote.presentation.shared.DefaultAppBar
import dev.logickoder.synote.presentation.shared.input.IconData
import dev.logickoder.synote.presentation.shared.input.InputField
import dev.logickoder.synote.presentation.shared.input.InputState
import java.util.*

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    notes: List<NoteEntity>,
    editNote: (String?) -> Unit,
    deleteNote: (String) -> Unit,
    onSearch: (String) -> Unit,
    isDarkMode: Boolean,
    switchDarkMode: () -> Unit,
) = Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
        DefaultAppBar(isDarkMode, switchDarkMode = switchDarkMode)
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
                    Note(note = note, deleteNote = deleteNote)
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
                Text(text = stringResource(id = R.string.new_note))
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
            placeholder = stringResource(id = R.string.search_notes),
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
        deleteNote = {},
        isDarkMode = false,
        switchDarkMode = {}
    )
}

@Preview
@Composable
private fun NotesScreenPreview() = SynoteTheme {
    NotesScreen(
        notes = (1..10).map {
            NoteEntity(
                id = it.toString(),
                userId = "userId$it",
                title = "Title $it",
                content = "Content $it",
                dateCreated = Date().toString(),
            )
        },
        editNote = {},
        onSearch = {},
        deleteNote = {},
        isDarkMode = true,
        switchDarkMode = {}
    )
}