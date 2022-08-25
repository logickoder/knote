package dev.logickoder.synote.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.logickoder.synote.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(vararg notes: NoteEntity)

    @Query("SELECT * FROM notes")
    abstract fun getNotes(): Flow<List<NoteEntity>>
}