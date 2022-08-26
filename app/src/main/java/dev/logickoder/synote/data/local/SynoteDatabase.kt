package dev.logickoder.synote.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.logickoder.synote.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class SynoteDatabase : RoomDatabase() {

    abstract fun notes(): NotesDao
}