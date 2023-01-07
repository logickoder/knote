package dev.logickoder.knote.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.knote.auth.data.repository.AuthRepository
import dev.logickoder.knote.auth.data.repository.AuthRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: AuthRepositoryImpl): AuthRepository
}