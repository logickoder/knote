package dev.logickoder.knote.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.knote.notes.data.repository.NotesRepository
import dev.logickoder.knote.notes.data.repository.NotesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: NotesRepositoryImpl): NotesRepository
}