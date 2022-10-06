package dev.logickoder.knote.notes.data.domain

import androidx.compose.runtime.Stable
import dev.logickoder.knote.notes.api.Note

@Stable
internal data class NoteDomain(
    val note: Note,
    val selected: Boolean,
)
