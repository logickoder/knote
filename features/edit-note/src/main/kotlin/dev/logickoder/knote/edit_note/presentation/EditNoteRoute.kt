package dev.logickoder.knote.edit_note.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.knote.notes.data.model.NoteId
import dev.logickoder.knote.ui.OnLifecycleEvent


@Composable
fun EditNoteRoute(
    id: dev.logickoder.knote.notes.data.model.NoteId?,
    modifier: Modifier = Modifier,
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
        editedAt = viewModel.editedAt,
        navigateBack = goBack,
        onTitleChanged = viewModel::updateTitle,
        onContentChanged = viewModel::updateContent,
        performAction = { action ->
            viewModel.performAction(action)
            goBack()
        },
    )
}