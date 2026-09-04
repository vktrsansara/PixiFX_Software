package com.vktrsansara.app.pixifx.domain.usecase

import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.repository.IDeviceRepository
import kotlinx.coroutines.flow.Flow

class DiscoverDevicesUseCase(
    private val deviceRepository: IDeviceRepository
) {
    operator fun invoke(): Flow<List<Device>> =
        deviceRepository.discoverDevices()
}
