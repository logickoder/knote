package dev.logickoder.synote.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

typealias LocalStore = DataStore<Preferences>

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    private val Context.local: LocalStore by preferencesDataStore(
        name = "synote"
    )

    @Singleton
    @Provides
    fun dataStore(@ApplicationContext context: Context) = context.local
}