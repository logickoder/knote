package dev.logickoder.synote.notes.impl.data.local

import androidx.room.*
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.impl.data.model.NoteEntity
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