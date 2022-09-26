package dev.logickoder.synote.edit_note.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.synote.notes.api.NoteId


@Composable
fun EditNoteRoute(
    id: NoteId?,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    switchDarkMode: () -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel = viewModel<EditNoteViewModel>()
    val note by viewModel.note.collectAsState()

    LaunchedEffect(key1 = id, block = {
        viewModel.getNote(id)
    })

    EditNoteScreen(
        modifier = modifier,
        title = note?.title ?: "",
        content = note?.content ?: "",
        isDarkMode = isDarkMode,
        switchDarkMode = switchDarkMode,
        navigateBack = navigateBack,
        onTitleChanged = {},
        onContentChanged = {},
    )
}