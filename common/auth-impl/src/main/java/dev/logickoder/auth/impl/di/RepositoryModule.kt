package dev.logickoder.auth.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.auth.impl.data.repository.AuthRepositoryImpl
import dev.logickoder.synote.auth.api.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: AuthRepositoryImpl): AuthRepository
}