package dev.logickoder.synote.notes.impl.data.local

import androidx.room.TypeConverter
import dev.logickoder.synote.notes.api.NoteId
import java.time.LocalDateTime
import java.time.ZoneOffset

internal object Converters {

    @TypeConverter
    @JvmStatic
    fun toNoteIdString(noteId: NoteId?) = noteId?.id

    @TypeConverter
    @JvmStatic
    fun fromNoteIdString(noteId: Long?) = noteId?.let { NoteId(it) }

    @TypeConverter
    @JvmStatic
    fun toDateLong(date: LocalDateTime?) = date?.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    @JvmStatic
    fun fromDateLong(date: Long?) = date?.let {
        LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
    }
}