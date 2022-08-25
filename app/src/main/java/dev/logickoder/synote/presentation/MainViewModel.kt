package dev.logickoder.synote.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.data.repository.AuthRepository
import dev.logickoder.synote.data.repository.SettingsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    var startingRoute: Navigation.Route? by mutableStateOf(null)
        private set

    var isDarkMode by mutableStateOf(false)
        private set

    init {
        initStates()
    }

    private fun initStates() = viewModelScope.launch {
        launch {
            authRepository.currentUser.take(1).collectLatest { user ->
                startingRoute = user?.let {
                    Navigation.Route.Notes
                } ?: Navigation.Route.Login
            }
        }
        launch {
            settingsRepository.darkMode.collectLatest { isDarkMode = it }
        }
    }

    fun switchDarkMode() {
        viewModelScope.launch {
            settingsRepository.setDarkMode(!isDarkMode)
        }
    }
}