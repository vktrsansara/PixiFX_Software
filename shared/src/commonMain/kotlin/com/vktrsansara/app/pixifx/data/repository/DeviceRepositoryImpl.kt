package com.vktrsansara.app.pixifx.data.repository

import com.vktrsansara.app.pixifx.data.discovery.INetworkDiscovery
import com.vktrsansara.app.pixifx.data.network.DeviceTransportClient
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.repository.IDeviceRepository
import kotlinx.coroutines.flow.Flow

class DeviceRepositoryImpl(
    private val discoveryService: INetworkDiscovery,
    private val transportClient: DeviceTransportClient
) : IDeviceRepository {

    override fun discoverDevices(): Flow<List<Device>> =
        discoveryService.discoverDevices()

    override suspend fun getDeviceInfo(ip: String): Result<Device> =
        transportClient.getDeviceInfo(ip).map { it.toDomain(fallbackIp = ip) }

    override suspend fun connectToDevice(device: Device): Result<Boolean> =
        transportClient.getDeviceInfo(device.ip).map { true }
}
