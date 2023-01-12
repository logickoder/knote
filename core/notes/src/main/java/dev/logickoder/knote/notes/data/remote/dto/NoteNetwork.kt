package dev.logickoder.knote.notes.data.remote.dto

import dev.logickoder.knote.notes.data.model.NoteAction

/***
 * The note saved to the cloud
 */
internal data class NoteNetwork(
    val id: Long = 0L,
    val title: String = "",
    val content: String = "",
    val dateCreated: Long = id,
    val dateModified: Long = dateCreated,
    val action: NoteAction? = null,
)
