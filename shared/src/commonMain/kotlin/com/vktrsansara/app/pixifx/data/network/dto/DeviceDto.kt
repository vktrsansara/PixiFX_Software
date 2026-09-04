package com.vktrsansara.app.pixifx.data.network.dto

import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.model.DeviceMode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    @SerialName("device") val device: String? = null,
    @SerialName("device_id") val deviceId: String,
    @SerialName("mode") val mode: Int = 0,
    @SerialName("is_setup") val isSetup: Boolean = false,
    @SerialName("ip") val ip: String? = null
) {
    fun toDomain(fallbackIp: String): Device {
        val resolvedIp = ip?.takeIf { it.isNotBlank() } ?: fallbackIp
        return Device(
            id = deviceId,
            ip = resolvedIp,
            mode = DeviceMode.fromCode(mode),
            isSetup = isSetup,
            name = deviceId,
            deviceType = device ?: "pixifx-device-esp8266"
        )
    }
}
