package dev.logickoder.synote.notes.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.synote.notes.api.NotesRepository
import dev.logickoder.synote.notes.impl.data.repository.NotesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: NotesRepositoryImpl): NotesRepository
}