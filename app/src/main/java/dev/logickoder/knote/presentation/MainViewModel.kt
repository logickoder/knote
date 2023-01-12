package dev.logickoder.knote.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bumble.appyx.navmodel.backstack.BackStack
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.knote.auth.data.repository.AuthRepository
import dev.logickoder.knote.navigation.Navigation
import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.notes.worker.NotesSyncWorker
import dev.logickoder.knote.settings.data.model.Theme
import dev.logickoder.knote.settings.data.repository.SettingsRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    settingsRepository: SettingsRepository,
    app: Application,
) : AndroidViewModel(app) {

    private val _screen = runBlocking {
        val route = authRepository.currentUser.first()?.let {
            Navigation.Route.NoteList(NoteListScreen.Notes)
        } ?: Navigation.Route.Login
        MutableStateFlow(route)
    }
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

    fun watchBackStackChanges(backStack: BackStack<Navigation.Route>) {
        viewModelScope.launch {
            backStack.elements.collectLatest { element ->
                val target = element.last().key.navTarget
                Napier.d { "Updating target: $target" }
                _screen.update { target }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    override fun onCleared() {
        NotesSyncWorker.sync(getApplication())
        super.onCleared()
    }
}