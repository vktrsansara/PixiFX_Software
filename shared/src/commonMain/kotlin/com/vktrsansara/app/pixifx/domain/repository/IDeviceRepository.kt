package com.vktrsansara.app.pixifx.domain.repository

import com.vktrsansara.app.pixifx.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface IDeviceRepository {
    fun discoverDevices(): Flow<List<Device>>
    suspend fun getDeviceInfo(ip: String): Result<Device>
    suspend fun connectToDevice(device: Device): Result<Boolean>
}
