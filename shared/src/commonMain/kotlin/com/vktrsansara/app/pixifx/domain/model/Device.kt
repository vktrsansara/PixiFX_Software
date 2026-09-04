package com.vktrsansara.app.pixifx.domain.model

data class Device(
    val id: String,
    val ip: String,
    val mode: DeviceMode,
    val isSetup: Boolean,
    val name: String = id,
    val deviceType: String = "pixifx-device-esp8266"
)
