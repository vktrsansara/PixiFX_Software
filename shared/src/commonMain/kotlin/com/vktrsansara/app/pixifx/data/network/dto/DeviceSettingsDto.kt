package com.vktrsansara.app.pixifx.data.network.dto

import com.vktrsansara.app.pixifx.domain.model.DeviceMode
import com.vktrsansara.app.pixifx.domain.model.DeviceSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceSettingsDto(
    @SerialName("hardware") val hardware: HardwareDto? = null,
    @SerialName("network") val network: NetworkDto? = null
) {
    fun toDomain(): DeviceSettings {
        val hw = hardware
        val net = network
        val sta = net?.sta
        val ap = net?.ap

        return DeviceSettings(
            baudRate = hw?.baudRate ?: 115200,
            mode = DeviceMode.fromCode(net?.mode ?: 1),
            hostname = net?.hostname ?: "pixifx",
            staSsid = sta?.ssid.orEmpty(),
            staPass = sta?.pass.orEmpty(),
            staTimeout = sta?.timeout ?: 30,
            staDhcp = sta?.dhcp ?: true,
            staIp = sta?.ip ?: "192.168.1.150",
            staGw = sta?.gw ?: "192.168.1.1",
            staMask = sta?.mask ?: "255.255.255.0",
            apSsid = ap?.ssid.orEmpty(),
            apPass = ap?.pass ?: "XmcUM1cx",
            apMaxConn = ap?.maxConn ?: 4,
            apDhcp = ap?.dhcp ?: false,
            apIp = ap?.ip ?: "10.10.1.1",
            apGw = ap?.gw ?: "10.10.1.1",
            apMask = ap?.mask ?: "255.255.255.0"
        )
    }

    companion object {
        fun fromDomain(settings: DeviceSettings): DeviceSettingsDto {
            return DeviceSettingsDto(
                hardware = HardwareDto(baudRate = settings.baudRate),
                network = NetworkDto(
                    mode = settings.mode.code,
                    hostname = settings.hostname,
                    sta = StaDto(
                        ssid = settings.staSsid,
                        pass = settings.staPass,
                        timeout = settings.staTimeout,
                        dhcp = settings.staDhcp,
                        ip = settings.staIp,
                        gw = settings.staGw,
                        mask = settings.staMask
                    ),
                    ap = ApDto(
                        ssid = settings.apSsid,
                        pass = settings.apPass,
                        maxConn = settings.apMaxConn,
                        dhcp = settings.apDhcp,
                        ip = settings.apIp,
                        gw = settings.apGw,
                        mask = settings.apMask
                    )
                )
            )
        }
    }
}

@Serializable
data class HardwareDto(
    @SerialName("baud_rate") val baudRate: Long = 115200
)

@Serializable
data class NetworkDto(
    @SerialName("mode") val mode: Int = 1,
    @SerialName("hostname") val hostname: String = "pixifx",
    @SerialName("sta") val sta: StaDto? = null,
    @SerialName("ap") val ap: ApDto? = null
)

@Serializable
data class StaDto(
    @SerialName("ssid") val ssid: String = "",
    @SerialName("pass") val pass: String = "",
    @SerialName("timeout") val timeout: Long = 30,
    @SerialName("dhcp") val dhcp: Boolean = true,
    @SerialName("ip") val ip: String = "192.168.1.150",
    @SerialName("gw") val gw: String = "192.168.1.1",
    @SerialName("mask") val mask: String = "255.255.255.0"
)

@Serializable
data class ApDto(
    @SerialName("ssid") val ssid: String = "",
    @SerialName("pass") val pass: String = "XmcUM1cx",
    @SerialName("max_conn") val maxConn: Int = 4,
    @SerialName("dhcp") val dhcp: Boolean = false,
    @SerialName("ip") val ip: String = "10.10.1.1",
    @SerialName("gw") val gw: String = "10.10.1.1",
    @SerialName("mask") val mask: String = "255.255.255.0"
)
