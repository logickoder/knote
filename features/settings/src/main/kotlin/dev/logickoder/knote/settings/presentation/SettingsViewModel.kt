package dev.logickoder.knote.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.knote.settings.api.SettingsRepository
import dev.logickoder.knote.settings.api.SettingsToggle
import dev.logickoder.knote.settings.api.Theme
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
) : ViewModel() {

    val theme = repository.theme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Theme.System,
    )

    val toggles = repository.toggles.map {
        it.toImmutableMap()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = persistentMapOf(),
    )

    fun updateTheme(theme: Theme) {
        viewModelScope.launch {
            repository.setTheme(theme)
        }
    }

    fun toggle(toggle: SettingsToggle) {
        viewModelScope.launch {
            repository.toggle(toggle)
        }
    }
}