package dev.logickoder.knote.notes.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [LocalModule::class, RepositoryModule::class])
@InstallIn(SingletonComponent::class)
class NotesModule