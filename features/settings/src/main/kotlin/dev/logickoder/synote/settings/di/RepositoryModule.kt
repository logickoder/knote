package dev.logickoder.synote.settings.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.logickoder.synote.settings.api.SettingsRepository
import dev.logickoder.synote.settings.data.repository.SettingsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: SettingsRepositoryImpl): SettingsRepository
}