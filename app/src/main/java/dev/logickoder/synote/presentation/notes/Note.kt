package dev.logickoder.synote.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.data.model.NoteEntity

@Composable
fun Note(
    note: NoteEntity,
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium)
        .padding(padding()),
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

