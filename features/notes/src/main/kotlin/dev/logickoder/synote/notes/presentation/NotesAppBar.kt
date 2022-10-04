package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.notes.R
import dev.logickoder.synote.notes.api.NoteAction
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.padding

@Composable
internal fun NotesAppBar(
    search: String,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    openDrawer: () -> Unit,
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
                onClick = openDrawer,
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
    screen: NotesDrawerItem,
    modifier: Modifier = Modifier,
    cancelSelection: () -> Unit,
    performAction: (NoteAction?) -> Unit,
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
            if (screen != NotesDrawerItem.Notes) {
                IconButton(
                    onClick = {
                        performAction(null)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Restore,
                            contentDescription = stringResource(R.string.notes_restore),
                        )
                    }
                )
            }
            NoteAction.values().forEach { action ->
                when {
                    action == NoteAction.Archive && screen == NotesDrawerItem.Notes -> {
                        action.Button(performAction = performAction)
                    }
                    action == NoteAction.Trash && screen != NotesDrawerItem.Archive -> {
                        action.Button(performAction = performAction)
                    }
                }
            }
        }
    )
}

@Composable
private fun NoteAction.Button(
    modifier: Modifier = Modifier,
    performAction: (NoteAction) -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = {
            performAction(this)
        },
        content = {
            Icon(
                imageVector = NotesDrawerItem.valueOf(name).icon,
                contentDescription = stringResource(
                    R.string.notes_perform_action,
                    name
                ),
            )
        }
    )
}

@Preview
@Composable
private fun NotesAppBarPreview() = SynoteTheme {
    NotesAppBar(
        search = "",
        onSearch = {},
        openDrawer = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun NotesInSelectionAppBarPreview() = SynoteTheme {
    NotesInSelectionAppBar(
        selected = 1,
        noteCount = 18,
        screen = NotesDrawerItem.Notes,
        modifier = Modifier.fillMaxWidth(),
        cancelSelection = { /*TODO*/ },
        performAction = { /*TODO*/ }
    )
}

@Preview
@Composable
private fun NotesInReverseSelectionAppBarPreview() = SynoteTheme {
    NotesInSelectionAppBar(
        selected = 1,
        noteCount = 18,
        screen = NotesDrawerItem.Trash,
        modifier = Modifier.fillMaxWidth(),
        cancelSelection = { /*TODO*/ },
        performAction = { /*TODO*/ }
    )
}