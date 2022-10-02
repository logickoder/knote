package dev.logickoder.synote.notes.data.domain

import androidx.compose.runtime.Stable
import dev.logickoder.synote.notes.api.Note

@Stable
data class NoteDomain(
    val note: Note,
    val selected: Boolean,
)
