package com.vktrsansara.app.pixifx.domain.model

data class DeviceSettings(
    val baudRate: Long = 115200,
    val mode: DeviceMode = DeviceMode.MASTER,
    val hostname: String = "pixifx",
    val staSsid: String = "",
    val staPass: String = "",
    val staTimeout: Long = 30,
    val staDhcp: Boolean = true,
    val staIp: String = "192.168.1.150",
    val staGw: String = "192.168.1.1",
    val staMask: String = "255.255.255.0",
    val apSsid: String = "",
    val apPass: String = "XmcUM1cx",
    val apMaxConn: Int = 4,
    val apDhcp: Boolean = false,
    val apIp: String = "10.10.1.1",
    val apGw: String = "10.10.1.1",
    val apMask: String = "255.255.255.0"
)
