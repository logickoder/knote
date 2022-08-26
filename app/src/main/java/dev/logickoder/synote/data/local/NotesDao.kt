package dev.logickoder.synote.data.local

import androidx.room.*
import dev.logickoder.synote.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(vararg notes: NoteEntity)

    @Query("SELECT * FROM notes")
    abstract fun getNotes(): Flow<List<NoteEntity>>

    @Delete
    abstract suspend fun delete(vararg notes: NoteEntity)
}