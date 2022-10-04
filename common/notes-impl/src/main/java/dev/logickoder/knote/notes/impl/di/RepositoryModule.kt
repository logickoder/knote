package dev.logickoder.knote.notes.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.knote.notes.api.NotesRepository
import dev.logickoder.knote.notes.impl.data.repository.NotesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: NotesRepositoryImpl): NotesRepository
}