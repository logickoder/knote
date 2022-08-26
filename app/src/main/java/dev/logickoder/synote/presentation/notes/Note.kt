package dev.logickoder.synote.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import dev.logickoder.synote.R
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.data.model.NoteEntity

@Composable
fun Note(
    note: NoteEntity,
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium)
        .padding(padding()),
    content = {
        Column(
            verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
            content = {
                if (note.title.isNotBlank()) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.W500,
                        )
                    )
                }
                if (note.content.isNotBlank()) {
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1
                    )
                }
            }
        )
        Box(modifier = Modifier.align(Alignment.TopEnd), content = {
            var showMenu by remember { mutableStateOf(false) }
            Icon(
                modifier = Modifier
                    .clickable {
                        showMenu = true
                    },
                painter = rememberVectorPainter(image = Icons.Outlined.MoreVert),
                contentDescription = null,
            )
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    onClick = {

                    },
                    content = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Outlined.ContentCopy),
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = android.R.string.copy),
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                )
                DropdownMenuItem(
                    onClick = {

                    },
                    content = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Outlined.Delete),
                            tint = MaterialTheme.colors.error,
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = R.string.delete),
                            color = MaterialTheme.colors.error,
                        )
                    }
                )
            }
        })
    }
)

