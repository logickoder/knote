package dev.logickoder.synote.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import dev.logickoder.synote.core.theme.AppTheme
import dev.logickoder.synote.core.theme.padding
import dev.logickoder.synote.core.theme.secondaryPadding
import dev.logickoder.synote.data.model.NoteEntity

@Composable
fun Note(
    note: NoteEntity,
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .background(AppTheme.colors.surface, shape = AppTheme.shapes.medium)
        .padding(padding()),
    verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
    content = {
        Text(
            text = note.title,
            style = AppTheme.typography.body1.copy(
                fontWeight = FontWeight.W500,
            )
        )
        Text(
            text = note.content,
            style = AppTheme.typography.body2.copy(
                color = AppTheme.colors.onSurface,
            ),
            maxLines = 1
        )
    }
)

