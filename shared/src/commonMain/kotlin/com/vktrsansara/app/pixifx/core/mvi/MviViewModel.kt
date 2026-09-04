package com.vktrsansara.app.pixifx.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base ViewModel implementing the MVI (Model-View-Intent) pattern.
 *
 * @param State The immutable UI state type.
 * @param Intent The user action / intent type.
 * @param Effect The one-time UI effect type (navigation, snackbars, etc.).
 */
abstract class MviViewModel<State : UiState, Intent : UiIntent, Effect : UiEffect>(
    initialState: State
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    protected val currentState: State
        get() = _uiState.value

    /**
     * Entry point for processing user intents.
     */
    abstract fun processIntent(intent: Intent)

    /**
     * Updates the UI state atomically using a reducer function.
     */
    protected fun setState(reducer: State.() -> State) {
        _uiState.update(reducer)
    }

    /**
     * Sends a one-off side effect to the UI.
     */
    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}
