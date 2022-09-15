package dev.logickoder.synote.presentation.edit_note

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.SynoteTheme
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.presentation.shared.BrandAppBar
import java.time.LocalDateTime

@Composable
fun EditNoteScreen(
    note: NoteEntity,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    switchDarkMode: () -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BrandAppBar(
                text = stringResource(id = R.string.edit_note),
                isDarkMode = isDarkMode,
                switchDarkMode = switchDarkMode,
                navigation = Icons.Default.KeyboardArrowLeft to navigateBack,
            )
        },
        content = {
            NoteEditor(note)
        }
    )
}

@Preview
@Composable
fun EditNoteScreenPreview() = SynoteTheme {
    EditNoteScreen(
        note = NoteEntity(
            id = "1",
            userId = "1111",
            title = "Stub note",
            content = "1111111111111111111",
            dateCreated = LocalDateTime.now().toString(),
        ),
        isDarkMode = false,
        switchDarkMode = {},
        navigateBack = {}
    )
}