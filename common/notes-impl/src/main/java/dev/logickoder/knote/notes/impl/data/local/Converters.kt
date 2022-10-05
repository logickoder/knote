package dev.logickoder.knote.notes.impl.data.local

import androidx.room.TypeConverter
import dev.logickoder.knote.notes.api.NoteAction
import dev.logickoder.knote.notes.api.NoteId
import java.time.LocalDateTime
import java.time.ZoneOffset

internal object Converters {

    @TypeConverter
    @JvmStatic
    fun NoteId?.toLong() = this?.id

    @TypeConverter
    @JvmStatic
    fun Long?.toNoteId() = this?.let { NoteId(it) }

    @TypeConverter
    @JvmStatic
    fun LocalDateTime?.toLong() = this?.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    @JvmStatic
    fun Long?.toDate() = this?.let {
        LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    @JvmStatic
    fun NoteAction?.toText() = this?.name

    @TypeConverter
    @JvmStatic
    fun String?.toNoteAction() = this?.let { data ->
        NoteAction.valueOf(data)
    }
}