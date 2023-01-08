package dev.logickoder.knote.note_list.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.knote.note_list.R
import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.notes.data.model.NoteAction
import dev.logickoder.knote.ui.theme.KNoteTheme
import dev.logickoder.knote.ui.theme.padding

@Composable
internal fun NoteListAppBar(
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
    )
}


@Composable
internal fun NoteListInSelectionAppBar(
    selected: Int,
    noteCount: Int,
    screen: NoteListScreen,
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
            if (screen != NoteListScreen.Notes) {
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
                    action == NoteAction.Archive && screen == NoteListScreen.Notes -> {
                        action.Button(performAction = performAction)
                    }

                    action == NoteAction.Trash && screen != NoteListScreen.Archive -> {
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
                imageVector = NoteListScreen.valueOf(name).icon,
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
private fun NoteListAppBarPreview() = KNoteTheme {
    NoteListAppBar(
        search = "",
        onSearch = {},
        openDrawer = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun NoteListInSelectionAppBarPreview() = KNoteTheme {
    NoteListInSelectionAppBar(
        selected = 1,
        noteCount = 18,
        screen = NoteListScreen.Notes,
        modifier = Modifier.fillMaxWidth(),
        cancelSelection = { /*TODO*/ },
        performAction = { /*TODO*/ }
    )
}

@Preview
@Composable
private fun NoteListInReverseSelectionAppBarPreview() = KNoteTheme {
    NoteListInSelectionAppBar(
        selected = 1,
        noteCount = 18,
        screen = NoteListScreen.Trash,
        modifier = Modifier.fillMaxWidth(),
        cancelSelection = { /*TODO*/ },
        performAction = { /*TODO*/ }
    )
}