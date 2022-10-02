package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.notes.R
import dev.logickoder.synote.ui.theme.padding

@Composable
internal fun NotesAppBar(
    search: String,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.padding(horizontal = padding()),
        value = search,
        onValueChange = onSearch,
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        ),
        placeholder = {
            Text(stringResource(id = R.string.notes_search))
        },
        leadingIcon = {
            IconButton(
                onClick = { /*TODO*/ },
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = null,
                    )
                }
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { /*TODO*/ },
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                    )
                }
            )
        }
    )
}


@Composable
internal fun NotesInSelectionAppBar(
    selected: Int,
    noteCount: Int,
    modifier: Modifier = Modifier,
    cancelSelection: () -> Unit,
    deleteNotes: () -> Unit,
    archiveNotes: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = cancelSelection,
                content = {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(R.string.notes_cancel_selection)
                    )
                }
            )
        },
        title = {
            Text(text = "$selected/$noteCount")
        },
        actions = {
            IconButton(
                onClick = archiveNotes,
                content = {
                    Icon(
                        imageVector = Icons.Default.Archive,
                        contentDescription = stringResource(R.string.notes_archive_notes)
                    )
                }
            )
            IconButton(
                onClick = deleteNotes,
                content = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.notes_delete_notes)
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun NotesAppBarPreview() {
    NotesAppBar(search = "", onSearch = {}, modifier = Modifier.fillMaxWidth())
}

@Preview
@Composable
private fun NotesInSelectionAppBarPreview() {
    NotesInSelectionAppBar(
        selected = 1,
        noteCount = 18,
        modifier = Modifier.fillMaxWidth(),
        cancelSelection = { /*TODO*/ },
        deleteNotes = { /*TODO*/ }) {

    }
}