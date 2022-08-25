package dev.logickoder.synote.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "notes")
@Serializable
data class NoteEntity(
    @PrimaryKey
    val id: String,
    @SerialName(value = "user_id")
    @ColumnInfo(name = "user_id")
    val userId: String,
    val title: String,
    val content: String,
    @SerialName(value = "date_created")
    @ColumnInfo(name = "date_created")
    val dateCreated: String,
    @SerialName(value = "date_modified")
    @ColumnInfo(name = "date_modified")
    val dateModified: String? = null,
)