package dev.logickoder.knote.notes.impl.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.logickoder.knote.notes.impl.data.local.NotesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class LocalModule {

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): NotesDatabase = Room.databaseBuilder(
        context, NotesDatabase::class.java, "notes"
    ).build()

    @Provides
    fun notesDao(database: NotesDatabase) = database.notes()
}