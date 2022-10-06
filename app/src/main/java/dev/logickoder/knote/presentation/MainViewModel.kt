package dev.logickoder.knote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.replace
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.knote.auth.api.AuthRepository
import dev.logickoder.knote.navigation.Navigation
import dev.logickoder.knote.notes.data.domain.NoteScreen
import dev.logickoder.knote.settings.api.SettingsRepository
import dev.logickoder.knote.settings.api.Theme
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    settingsRepository: SettingsRepository,
) : ViewModel() {

    private var backStack: BackStack<Navigation.Route>? = null

    val startScreen = authRepository.currentUser.take(1).map { user ->
        val result = user?.let {
            Navigation.Route.Notes(NoteScreen.Notes)
        } ?: Navigation.Route.Login
        updateScreen(result)
        result
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    private val _screen = MutableStateFlow<Navigation.Route>(Navigation.Route.Login)
    val screen = _screen.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _screen.value
    )

    val theme = settingsRepository.theme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Theme.System
    )

    fun initNavigation(backStack: BackStack<Navigation.Route>) {
        this.backStack = backStack
    }

    fun updateScreen(route: Navigation.Route) {
        viewModelScope.launch {
            _screen.emit(route)
        }
    }

    fun pop(backStack: BackStack<Navigation.Route>) {
        kotlin.runCatching {
            backStack.elements.value.let {
                val target = it[it.lastIndex - 1].key.navTarget
                Napier.d("Popping to target: $target")
                backStack.replace(target)
                updateScreen(target)
            }
        }.also {
            if (it.isFailure) {
                Napier.e("Failed to get previous backstack entry", it.exceptionOrNull())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    override fun onCleared() {
        super.onCleared()
        backStack = null
    }
}