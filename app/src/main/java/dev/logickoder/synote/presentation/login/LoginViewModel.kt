package dev.logickoder.synote.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.core.domain.MutableObservableState
import dev.logickoder.synote.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {
    val isLogin = MutableObservableState(
        initial = true,
        update = { input: Boolean, _ -> input },
        output = { it },
    )

    val username = MutableObservableState<String?, String?, String>(
        initial = null,
        update = { it, _ -> it },
        output = { it ?: "" }
    )

    val password = MutableObservableState<String?, String?, String>(
        initial = null,
        update = { it, _ -> it },
        output = { it ?: "" }
    )

    fun performAuth() {
        viewModelScope.launch {
            if (isLogin.value) {
                repository.login(username.value!!, password.value!!)
            } else {
                repository.register(username.value!!, password.value!!)
            }
        }
    }
}