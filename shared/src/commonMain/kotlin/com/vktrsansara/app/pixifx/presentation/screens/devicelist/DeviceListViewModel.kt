package com.vktrsansara.app.pixifx.presentation.screens.devicelist

import androidx.lifecycle.viewModelScope
import com.vktrsansara.app.pixifx.core.mvi.MviViewModel
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.repository.IDeviceRepository
import com.vktrsansara.app.pixifx.domain.usecase.ConnectToDeviceUseCase
import com.vktrsansara.app.pixifx.domain.usecase.DiscoverDevicesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DeviceListViewModel(
    private val discoverDevicesUseCase: DiscoverDevicesUseCase,
    private val connectToDeviceUseCase: ConnectToDeviceUseCase,
    private val deviceRepository: IDeviceRepository
) : MviViewModel<DeviceListState, DeviceListIntent, DeviceListEffect>(DeviceListState()) {

    private var discoveryJob: Job? = null

    init {
        startDiscovery()
    }

    override fun processIntent(intent: DeviceListIntent) {
        when (intent) {
            is DeviceListIntent.StartDiscovery -> startDiscovery()
            is DeviceListIntent.ConnectToDevice -> connectToDevice(intent.device)
            is DeviceListIntent.ConnectDirectIp -> connectDirectIp(intent.ipOrHost)
            is DeviceListIntent.UpdateDirectIpInput -> setState { copy(directIpInput = intent.ip) }
            is DeviceListIntent.SetDirectIpDialogVisible -> setState { copy(showDirectIpDialog = intent.visible) }
            is DeviceListIntent.ClearError -> setState { copy(errorMessage = null) }
        }
    }

    private fun startDiscovery() {
        discoveryJob?.cancel()
        discoveryJob = viewModelScope.launch {
            discoverDevicesUseCase()
                .onStart {
                    setState { copy(isSearching = true, errorMessage = null) }
                }
                .catch { error ->
                    setState {
                        copy(
                            isSearching = false,
                            errorMessage = error.message ?: "Ошибка поиска устройств"
                        )
                    }
                    sendEffect(DeviceListEffect.ShowSnackbar("Ошибка при поиске: ${error.message}"))
                }
                .onCompletion {
                    setState { copy(isSearching = false) }
                }
                .collect { foundDevices ->
                    setState { copy(devices = foundDevices) }
                }
        }
    }

    private fun connectDirectIp(ipOrHost: String) {
        val cleanIp = ipOrHost.trim()
        if (cleanIp.isBlank()) {
            sendEffect(DeviceListEffect.ShowSnackbar("Введите IP-адрес или имя хоста"))
            return
        }

        viewModelScope.launch {
            setState { copy(isConnectingDirect = true) }
            val result = deviceRepository.getDeviceInfo(cleanIp)
            setState { copy(isConnectingDirect = false) }

            result.onSuccess { device ->
                setState {
                    val updatedList = if (devices.none { it.id == device.id }) devices + device else devices
                    copy(
                        devices = updatedList,
                        connectedDevice = device,
                        showDirectIpDialog = false
                    )
                }
                sendEffect(DeviceListEffect.ShowSnackbar("Подключено к ${device.name} ($cleanIp)"))
                sendEffect(DeviceListEffect.NavigateToController(device))
            }.onFailure { error ->
                sendEffect(DeviceListEffect.ShowSnackbar("Устройство на $cleanIp не отвечает (${error.message ?: "Таймаут"})"))
            }
        }
    }

    private fun connectToDevice(device: Device) {
        viewModelScope.launch {
            setState { copy(connectedDevice = device) }
            val result = connectToDeviceUseCase(device)
            if (result.isSuccess) {
                sendEffect(DeviceListEffect.ShowSnackbar("Подключено к ${device.name}"))
                sendEffect(DeviceListEffect.NavigateToController(device))
            } else {
                sendEffect(DeviceListEffect.ShowSnackbar("Не удалось подключиться к ${device.ip}"))
            }
        }
    }
}
