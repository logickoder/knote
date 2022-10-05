package dev.logickoder.knote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.knote.auth.api.AuthRepository
import dev.logickoder.knote.navigation.Navigation
import dev.logickoder.knote.settings.api.SettingsRepository
import dev.logickoder.knote.settings.api.Theme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    settingsRepository: SettingsRepository,
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

    val theme = settingsRepository.theme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Theme.System
    )

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogout()
        }
    }
}