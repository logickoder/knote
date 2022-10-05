package dev.logickoder.knote.notes.impl.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.logickoder.knote.notes.impl.data.model.NoteEntity

@TypeConverters(Converters::class)
@Database(entities = [NoteEntity::class], version = 1)
internal abstract class NotesDatabase : RoomDatabase() {

    abstract fun notes(): NotesDao
}