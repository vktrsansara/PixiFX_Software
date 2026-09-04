package com.vktrsansara.app.pixifx.data.discovery

import com.vktrsansara.app.pixifx.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface INetworkDiscovery {
    fun discoverDevices(): Flow<List<Device>>
}
