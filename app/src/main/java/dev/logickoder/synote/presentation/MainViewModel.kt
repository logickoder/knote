package dev.logickoder.synote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.auth.api.AuthRepository
import dev.logickoder.synote.data.repository.SettingsRepository
import dev.logickoder.synote.navigation.Navigation
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val startingRoute = authRepository.currentUser.map { user ->
        user?.let {
            Navigation.Route.Notes
        } ?: Navigation.Route.Login
    }.take(1).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    val darkMode = settingsRepository.darkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    fun switchDarkMode() {
        viewModelScope.launch {
            settingsRepository.toggleDarkMode()
        }
    }
}