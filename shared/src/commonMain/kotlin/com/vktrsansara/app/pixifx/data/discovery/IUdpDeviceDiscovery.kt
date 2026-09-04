package com.vktrsansara.app.pixifx.data.discovery

import com.vktrsansara.app.pixifx.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface IUdpDeviceDiscovery {
    fun discoverViaBroadcast(
        port: Int = 8080,
        payload: String = "PIXIFX_DISCOVER",
        listenDurationMs: Long = 2000
    ): Flow<Device>
}
