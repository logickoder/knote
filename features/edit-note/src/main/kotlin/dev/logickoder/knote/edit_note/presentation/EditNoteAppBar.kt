package dev.logickoder.knote.edit_note.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.logickoder.knote.edit_note.R
import dev.logickoder.knote.model.formatted
import dev.logickoder.knote.notes.data.model.NoteAction
import java.time.LocalDateTime

@Composable
internal fun EditNoteAppBar(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    performAction: (NoteAction) -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        content = {
            IconButton(
                onClick = navigateBack,
                content = {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            NoteAction.values().forEach { action ->
                IconButton(
                    onClick = {
                        performAction(action)
                    },
                    content = {
                        Icon(
                            imageVector = when (action) {
                                NoteAction.Archive -> Icons.Outlined.Archive
                                NoteAction.Trash -> Icons.Outlined.Delete
                            },
                            contentDescription = stringResource(
                                R.string.edit_note_perform_action,
                                action.name
                            )
                        )
                    }
                )
            }
        },
    )
}

@Composable
fun EditNoteBottomBar(
    editedAt: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        modifier = modifier,
        content = {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                stringResource(R.string.edit_note_edited_at, editedAt.formatted),
                style = MaterialTheme.typography.overline,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    )
}