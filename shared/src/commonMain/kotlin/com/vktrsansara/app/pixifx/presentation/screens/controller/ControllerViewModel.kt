package com.vktrsansara.app.pixifx.presentation.screens.controller

import androidx.lifecycle.viewModelScope
import com.vktrsansara.app.pixifx.core.mvi.MviViewModel
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.model.Effect
import com.vktrsansara.app.pixifx.domain.model.RgbColor
import com.vktrsansara.app.pixifx.domain.usecase.SetBrightnessUseCase
import com.vktrsansara.app.pixifx.domain.usecase.SetEffectUseCase
import kotlinx.coroutines.launch

class ControllerViewModel(
    private val setEffectUseCase: SetEffectUseCase,
    private val setBrightnessUseCase: SetBrightnessUseCase
) : MviViewModel<ControllerState, ControllerIntent, ControllerEffect>(ControllerState()) {

    override fun processIntent(intent: ControllerIntent) {
        when (intent) {
            is ControllerIntent.LoadDevice -> {
                setState { copy(device = intent.device) }
            }
            is ControllerIntent.SetBrightness -> {
                setState { copy(brightness = intent.brightness) }
                currentState.device?.ip?.let { ip ->
                    viewModelScope.launch {
                        setBrightnessUseCase(ip, intent.brightness)
                    }
                }
            }
            is ControllerIntent.SetColor -> {
                setState { copy(selectedColor = intent.color) }
            }
            is ControllerIntent.SelectEffect -> {
                setState { copy(currentEffect = intent.effect) }
                currentState.device?.ip?.let { ip ->
                    viewModelScope.launch {
                        setEffectUseCase(ip, intent.effect)
                    }
                }
            }
            is ControllerIntent.Disconnect -> {
                sendEffect(ControllerEffect.NavigateBack)
            }
        }
    }
}
