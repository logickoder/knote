package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.notes.R
import dev.logickoder.synote.ui.theme.padding

@Composable
internal fun NotesAppBar(
    search: String,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
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
                onClick = { /*TODO*/ },
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


@Preview
@Composable
private fun NotesAppBarPreview() {
    NotesAppBar(search = "", onSearch = {}, modifier = Modifier.fillMaxWidth())
}