package dev.logickoder.synote.edit_note.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.edit_note.R
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
                text = stringResource(id = R.string.edit_note),
                isDarkMode = isDarkMode,
                switchDarkMode = switchDarkMode,
                navigation = Icons.Default.KeyboardArrowLeft to navigateBack,
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding),
                content = {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        onValueChange = onTitleChanged,
                        textStyle = LocalTextStyle.current.copy(
                            fontWeight = FontWeight.Black,
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                        ),
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxSize(),
                        value = content,
                        onValueChange = onContentChanged,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.Transparent,
                        ),
                    )
                }
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