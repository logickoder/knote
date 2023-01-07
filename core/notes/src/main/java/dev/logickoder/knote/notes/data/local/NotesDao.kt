package dev.logickoder.knote.notes.data.local

import androidx.room.*
import dev.logickoder.knote.notes.data.model.NoteEntity
import dev.logickoder.knote.notes.data.model.NoteId
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(vararg notes: NoteEntity): Array<Long>

    @Query("SELECT * FROM notes")
    abstract fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE :id = id")
    abstract fun getNote(id: NoteId): Flow<NoteEntity>

    @Delete
    abstract suspend fun delete(vararg notes: NoteEntity)
}