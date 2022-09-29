package dev.logickoder.synote.edit_note.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.ui.BrandAppBar
import dev.logickoder.synote.ui.theme.SynoteTheme

@Composable
internal fun EditNoteScreen(
    title: String,
    content: String,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    switchDarkMode: () -> Unit,
    navigateBack: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BrandAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    OutlinedTextField(
                        value = title,
                        onValueChange = onTitleChanged,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        maxLines = 1,
                    )
                },
                isDarkMode = isDarkMode,
                switchDarkMode = switchDarkMode,
                navigation = Icons.Default.KeyboardArrowLeft to navigateBack,
            )
        },
        content = { padding ->
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                value = content,
                onValueChange = onContentChanged,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                ),
            )
        }
    )
}

@Preview
@Composable
private fun EditNoteScreenPreview() = SynoteTheme {
    EditNoteScreen(
        title = "Stub note",
        content = "111111111111111",
        isDarkMode = false,
        switchDarkMode = {},
        navigateBack = {},
        onContentChanged = {},
        onTitleChanged = {},
    )
}