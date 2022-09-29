package dev.logickoder.synote.edit_note.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.ui.OnLifecycleEvent


@Composable
fun EditNoteRoute(
    id: NoteId?,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    switchDarkMode: () -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel = viewModel<EditNoteViewModel>()
    val goBack: () -> Unit = remember {
        {
            viewModel.save()
            navigateBack()
        }
    }

    LaunchedEffect(key1 = id, block = {
        viewModel.getNote(id)
    })

    BackHandler {
        goBack()
    }

    OnLifecycleEvent(event = Lifecycle.Event.ON_PAUSE) {
        viewModel.save()
    }

    EditNoteScreen(
        modifier = modifier,
        title = viewModel.title,
        content = viewModel.content,
        isDarkMode = isDarkMode,
        switchDarkMode = switchDarkMode,
        navigateBack = goBack,
        onTitleChanged = viewModel::updateTitle,
        onContentChanged = viewModel::updateContent,
    )
}