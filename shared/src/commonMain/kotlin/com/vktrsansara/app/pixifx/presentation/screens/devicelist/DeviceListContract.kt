package com.vktrsansara.app.pixifx.presentation.screens.devicelist

import com.vktrsansara.app.pixifx.core.mvi.UiEffect
import com.vktrsansara.app.pixifx.core.mvi.UiIntent
import com.vktrsansara.app.pixifx.core.mvi.UiState
import com.vktrsansara.app.pixifx.domain.model.Device

data class DeviceListState(
    val isSearching: Boolean = false,
    val isConnectingDirect: Boolean = false,
    val devices: List<Device> = emptyList(),
    val errorMessage: String? = null,
    val connectedDevice: Device? = null,
    val directIpInput: String = "10.10.1.1",
    val showDirectIpDialog: Boolean = false
) : UiState

sealed interface DeviceListIntent : UiIntent {
    data object StartDiscovery : DeviceListIntent
    data class ConnectToDevice(val device: Device) : DeviceListIntent
    data class ConnectDirectIp(val ipOrHost: String) : DeviceListIntent
    data class UpdateDirectIpInput(val ip: String) : DeviceListIntent
    data class SetDirectIpDialogVisible(val visible: Boolean) : DeviceListIntent
    data object ClearError : DeviceListIntent
}

sealed interface DeviceListEffect : UiEffect {
    data class ShowSnackbar(val message: String) : DeviceListEffect
    data class NavigateToController(val device: Device) : DeviceListEffect
}
