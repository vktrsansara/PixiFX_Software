package com.vktrsansara.app.pixifx.presentation.screens.controller

import com.vktrsansara.app.pixifx.core.mvi.UiEffect
import com.vktrsansara.app.pixifx.core.mvi.UiIntent
import com.vktrsansara.app.pixifx.core.mvi.UiState
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.model.Effect
import com.vktrsansara.app.pixifx.domain.model.RgbColor

data class ControllerState(
    val device: Device? = null,
    val isConnecting: Boolean = false,
    val brightness: Int = 255,
    val selectedColor: RgbColor = RgbColor(255, 0, 0),
    val currentEffect: Effect? = null,
    val isOnline: Boolean = true
) : UiState

sealed interface ControllerIntent : UiIntent {
    data class LoadDevice(val device: Device) : ControllerIntent
    data class SetBrightness(val brightness: Int) : ControllerIntent
    data class SetColor(val color: RgbColor) : ControllerIntent
    data class SelectEffect(val effect: Effect) : ControllerIntent
    data object Disconnect : ControllerIntent
}

sealed interface ControllerEffect : UiEffect {
    data class ShowSnackbar(val message: String) : ControllerEffect
    data object NavigateBack : ControllerEffect
}
