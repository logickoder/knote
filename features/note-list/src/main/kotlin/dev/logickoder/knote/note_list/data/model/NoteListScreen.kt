package dev.logickoder.knote.note_list.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.ui.graphics.vector.ImageVector

enum class NoteListScreen(val icon: ImageVector) {
    Archive(Icons.Outlined.Archive),
    Notes(Icons.Outlined.Notes),
    Trash(Icons.Outlined.Delete),
}