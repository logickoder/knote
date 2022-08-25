package dev.logickoder.synote.presentation.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import dev.logickoder.synote.R
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.presentation.shared.AppButton
import dev.logickoder.synote.presentation.shared.DefaultAppBar
import dev.logickoder.synote.presentation.shared.input.InputField
import dev.logickoder.synote.presentation.shared.input.InputState

class NotesScreen(
    buildContext: BuildContext,
    val backStack: BackStack<Navigation.Route>,
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) = with(viewModel<NotesViewModel>()) {
        LaunchedEffect(key1 = Unit, block = {
            getNotes()
        })
        NotesScreenContent(
            modifier = modifier,
            notes = notes.collectAsState(initial = emptyList()).value,
            editNote = {
                editNote(it, backStack)
            },
            onSearch = {
                search(it)
            }
        )
    }
}

@Composable
private fun NotesScreenContent(
    modifier: Modifier = Modifier,
    notes: List<NoteEntity>,
    editNote: (String?) -> Unit,
    onSearch: (String) -> Unit,
) = Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
        DefaultAppBar()
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
                    Note(note = note)
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
            placeholder = stringResource(id = R.string.search_notes),
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun NotesScreenPreview() = NotesScreenContent(
    notes = emptyList(),
    editNote = {},
    onSearch = {}
)