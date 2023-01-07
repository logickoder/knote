package dev.logickoder.knote.notes.data.domain

import androidx.compose.runtime.Stable
import dev.logickoder.knote.notes.data.model.Note

@Stable
internal data class NoteDomain(
    val note: dev.logickoder.knote.notes.data.model.Note,
    val selected: Boolean,
)
