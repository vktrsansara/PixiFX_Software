package com.vktrsansara.app.pixifx.domain.usecase

import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.repository.IDeviceRepository

class ConnectToDeviceUseCase(
    private val deviceRepository: IDeviceRepository
) {
    suspend operator fun invoke(device: Device): Result<Boolean> =
        deviceRepository.connectToDevice(device)
}
